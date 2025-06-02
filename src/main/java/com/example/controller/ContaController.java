package com.example.controller;

import com.example.model.dao.ContaDAO;
import com.example.model.entity.Conta;
import com.example.model.entity.Transacao;
import com.example.model.impl.ContaSQLite;
import com.example.view.Cadastro;
import com.example.view.Login;
import com.example.view.TelaPrincipal;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContaController extends JFrame {

    final ContaDAO contaSQLite = new ContaSQLite();

    //Verifica informações de cadastro e tenta salvar no banco de dados
    public void cadastrar(String login, String senha, String confSenha, Cadastro view) {

        if (login.isEmpty() || senha.isEmpty() || confSenha.isEmpty()) {
            JOptionPane.showMessageDialog(ContaController.this, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!senha.equals(confSenha)) {
            JOptionPane.showMessageDialog(ContaController.this, "Senhas não conferem", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ Verifica se o login já existe antes de tentar salvar
        if (contaSQLite.loginExiste(login)) {
            JOptionPane.showMessageDialog(ContaController.this, "Usuário já existe! Escolha outro", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Conta conta = new Conta(login, senha);

            if (!contaSQLite.salvar(conta)) {
                JOptionPane.showMessageDialog(ContaController.this, "Não foi possível cadastrar a conta. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(ContaController.this, "Conta cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            view.dispose(); // fecha tela de cadastro

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ContaController.this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    //Verifica informações de login com consulta em banco de dados
    public void logar(String login, String senha, Login view) {

        try {

            //Verifica se os campos estão preenchidos
            if (login.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(ContaController.this, "Insira login e senha!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Faz a consulta do login e senha no banco de dados
            contaSQLite.logar(login, senha);

            //Acessa o menu principal
            view.dispose();
            SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));

            //Levanta erro para caso não encontre o email com a senha
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(ContaController.this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ContaController.this, "Não foi possivel se conectar. Tenta novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibirExtrato(
            String dataInicio,
            String dataFinal,
            String tipo,
            JTextArea resultadoArea) {

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inicio = dataInicio.isEmpty() ? LocalDate.MIN : LocalDate.parse(dataInicio, formatter);
            LocalDate fim = dataFinal.isEmpty() ? LocalDate.MAX : LocalDate.parse(dataFinal, formatter);

            List<String> tipos = new ArrayList<>();
            if (tipo.equals("Todos") || tipo.equals("Receita")) {
                tipos.add("Entrada");
            }
            if (tipo.equals("Todos") || tipo.equals("Despesa")) {
                tipos.add("Saida");
            }

            resultadoArea.setText("Data | Categoria | Descrição | Valor\n");
            for (Transacao transacao : contaSQLite.getTransacoes()) {
                boolean validaData = (transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim))
                        || transacao.getData().equals(inicio)
                        || transacao.getData().equals(fim);
                boolean validaTipo = tipos.contains(transacao.getTipo());

                if (validaData && validaTipo) {

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dataFormatada = dtf.format(transacao.getData());

                    String transacaoString = dataFormatada + " - " + transacao.getCategoria() + " - " + transacao.getDescricao() + " - R$" + transacao.getValor();
                    resultadoArea.append(transacaoString + "\n");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro na consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
