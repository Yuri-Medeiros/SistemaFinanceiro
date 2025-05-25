package com.example.controller;

import com.example.view.NovaTransacao;
import com.example.view.TelaPrincipal;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TransacaoController {

    public void adicionarTransacao(String valor,
                                   String categoria,
                                   String descricao,
                                   String tipo,
                                   String data,
                                   NovaTransacao view) {

        if(valor.isEmpty() ||
                tipo.isEmpty() ||
                categoria.isEmpty() ||
                descricao.isEmpty() ||
                data.contains("_")){

            JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(valor.matches("\\d+")){

            // Tenta converter para LocalDate
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            //Converte valor para real
            String aux = valor.replace(',', '.');
            float valorFormatted = Float.parseFloat(aux);

            try {
                LocalDate dataConvertida = LocalDate.parse(data, formato);

                if(tipo.equals("Receita")){
                    //TODO: depositar
                } else {
                    //TODO: sacar
                }

                JOptionPane.showMessageDialog(null,
                        "Transação adicionada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                view.dispose();
                SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Informe apenas números para Valor!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
