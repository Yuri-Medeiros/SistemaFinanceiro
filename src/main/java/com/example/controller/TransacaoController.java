package com.example.controller;

import com.example.model.dao.ContaDAO;
import com.example.model.dao.TransacaoDAO;
import com.example.model.entity.Transacao;
import com.example.model.impl.ContaSQLite;
import com.example.model.impl.TransacaoSQLite;
import com.example.Main;
import com.example.view.NovaTransacao;
import com.example.view.TelaPrincipal;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TransacaoController {

    private final TransacaoDAO transacaoSQLite = new TransacaoSQLite();
    private final ContaDAO contaSQLite = new ContaSQLite();

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
                    categoria,
                    descricao,
                    dataConvertida,
                    tipo);

            //Tenta registrar a transação
            if (!transacaoSQLite.salvar(transacao)) {

                JOptionPane.showMessageDialog(null, "Não foi possivel salvar sa transação. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Altera o saldo da conta
            contaSQLite.setSaldo(valorFormatted, tipo);

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
        }
    }
}
