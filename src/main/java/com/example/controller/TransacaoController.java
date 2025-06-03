package com.example.controller;

import com.example.model.dao.CategoriaDAO;
import com.example.model.dao.ContaDAO;
import com.example.model.dao.TransacaoDAO;
import com.example.model.entity.Transacao;
import com.example.model.impl.CategoriaSQLite;
import com.example.model.impl.ContaSQLite;
import com.example.model.impl.TransacaoSQLite;
import com.example.Main;
import com.example.view.NovaTransacao;
import com.example.view.TelaPrincipal;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TransacaoController {

    private final TransacaoDAO transacaoSQLite = new TransacaoSQLite();
    private final ContaDAO contaSQLite = new ContaSQLite();
    private final CategoriaDAO categoriaSQLite = new CategoriaSQLite();

    //Valida os dados e registra uma transação
    public void adicionarTransacao(String valor,
                                   String categoria,
                                   String descricao,
                                   String tipo,
                                   String data,
                                   NovaTransacao view) {

        //Verifica se todos os valores são validos
        if(valor.isEmpty() ||
                tipo.isEmpty() ||
                categoria.isEmpty() ||
                descricao.isEmpty() ||
                data.contains("_")){

            //Emiti erro para campos não preenchidos
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Verifica se o valor é valido
        if(!valor.matches("\\d+")){
            JOptionPane.showMessageDialog(null, "Informe apenas números para Valor!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;

        }

        //Converte valor para real
        String aux = valor.replace(',', '.');
        float valorFormatted = Float.parseFloat(aux);

        try {
            // Tenta converter para LocalDate
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataConvertida = LocalDate.parse(data, formato);

            //Instancia uma entidade transação
            Transacao transacao = new Transacao(valorFormatted,
                    categoriaSQLite.getCategoria(categoria),
                    descricao,
                    dataConvertida,
                    tipo);

            //Tenta registrar a transação
            transacaoSQLite.salvar(transacao);
            contaSQLite.setSaldo(valorFormatted, tipo);

            //JOptionPane.showMessageDialog(null, "Não foi possivel salvar sa transação. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);

            //Confirma transação para usuario
            JOptionPane.showMessageDialog(null,
                    "Transação adicionada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            //volta ao menu principal
            view.dispose();
            SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(null, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

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

            resultadoArea.setText("Data | Categoria | Descrição | Valor\n");
            for (Transacao transacao : transacaoSQLite.getTransacoesConsulta(inicio, fim, tipo)) {

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataFormatada = dtf.format(transacao.getData());

                String transacaoString = dataFormatada + " - " + transacao.getCategoria() + " - " + transacao.getDescricao() + " - R$" + transacao.getValor();
                resultadoArea.append(transacaoString + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
