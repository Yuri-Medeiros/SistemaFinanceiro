package com.example.view;

import com.example.controller.ContaController;
import com.example.controller.TransacaoController;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class Consulta extends JFrame {

    private JFormattedTextField campoDataFiltroInicial = null;
    private JFormattedTextField campoDataFiltroFinal = null;
    private final TransacaoController t = new TransacaoController();

    public Consulta() {

        //Configurações basicas do frame principal
        setVisible(true);

        setTitle("Extrato");
        setSize(600, 400);
        setLocationRelativeTo(null);

        //Cria painel principal
        JPanel panel = new JPanel(new BorderLayout());
        JPanel filtrosPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        //Adiciona titulos de campos ao painel
        panel.add(new JLabel("Data inicial:"));
        panel.add(new JLabel("Data final:"));

        //Cria campos de data
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');

            campoDataFiltroInicial = new JFormattedTextField(mascaraData);
            campoDataFiltroInicial.setColumns(10);

            campoDataFiltroFinal = new JFormattedTextField(mascaraData);
            campoDataFiltroFinal.setColumns(10);

            panel.add(campoDataFiltroInicial);
            panel.add(campoDataFiltroFinal);
        } catch (ParseException er) {
            JOptionPane.showMessageDialog(null, "Erro ao armazenar a data. Tente novamente!");
            return;
        }

        //Cria campo de tipo
        JLabel tipoLabel = new JLabel("Tipo:");
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Todos", "Receita", "Despesa"});

        //Adiciona campos de filtro no frame
        filtrosPanel.add(new JLabel("Data inicial:"));
        filtrosPanel.add(campoDataFiltroInicial);
        filtrosPanel.add(new JLabel("Data final:"));
        filtrosPanel.add(campoDataFiltroFinal);
        filtrosPanel.add(tipoLabel);
        filtrosPanel.add(tipoCombo);

        //Cria area de texto para resultados
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        //Cria botão de consulta
        JButton consultarButton = new JButton("Consultar");
        consultarButton.setBackground(new Color(0, 120, 215));
        consultarButton.setForeground(Color.WHITE);
        consultarButton.setFocusPainted(false);
        consultarButton.setFont(new Font("Arial", Font.BOLD, 14));

        //Configura ação de botão de consulta
        consultarButton.addActionListener(e ->
                t.exibirExtrato(
                        campoDataFiltroInicial.getText(),
                        campoDataFiltroFinal.getText(),
                        (String) tipoCombo.getSelectedItem(),
                        resultadoArea
                ));

        //Cria botão de cancelamento
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(new Color(215, 60, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        //Configura ação de botão de cancelamento
        cancelButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
        });

        //Adiciona botões no frame
        JPanel panelBotao = new JPanel();
        panelBotao.add(consultarButton);
        panelBotao.add(cancelButton);

        //Adiciona todos os frames ao painel
        panel.add(filtrosPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotao, BorderLayout.SOUTH);

        //Adiciona painel ao frame principal
        add(panel);
    }
}
