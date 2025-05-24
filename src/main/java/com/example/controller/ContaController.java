package com.example.controller;

import com.example.model.dao.ContaDAO;
import com.example.model.entity.Conta;
import com.example.model.impl.ContaSQLite;
import com.example.view.Cadastro;
import com.example.view.Login;
import com.example.view.TelaPrincipal;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContaController extends JFrame {

    ContaDAO SQLite = new ContaSQLite();

    //Verifica se o login ja existe no banco de dados
    public ActionListener cadastrar(String login, String senha, String confSenha, Cadastro view) {
        return new ActionListener() {

            //Armazena o Cadastro
            @Override
            public void actionPerformed(ActionEvent e) {

                if (login.isEmpty() || senha.isEmpty() || confSenha.isEmpty()) {
                    JOptionPane.showMessageDialog(ContaController.this, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!senha.equals(confSenha)) {
                    JOptionPane.showMessageDialog(ContaController.this, "Senhas não conferem", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Verifica se o Login ja existe
                try {

                    Conta conta = new Conta(login, senha);

                    if (!SQLite.salvar(conta)) {
                        JOptionPane.showMessageDialog(ContaController.this, "Não foi possivel cadastrar a conta. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    view.dispose();
                } catch (ConstraintViolationException ex){
                    //Exibe erro caso Login ja exista
                    JOptionPane.showMessageDialog(ContaController.this, "Usuário ja existe! Escolha outro", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    public ActionListener logar(String login, String senha, Login view) {
        return new ActionListener() {

            //Função executada pós acionamento do botão de login
            @Override
            public void actionPerformed(ActionEvent e) {

                //Verifica informações
                try {

                    SQLite.logar(login, senha);

                    //Fecha a tela e abre cadastro
                    view.dispose();
                    SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));

                } catch (IllegalArgumentException ex) {
                    //Se false, exibe mensagem de erro
                    JOptionPane.showMessageDialog(ContaController.this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ContaController.this, "Não foi possivel se conectar. Tenta novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }
}
