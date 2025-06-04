package com.example.view;

import com.example.controller.ContaController;
import com.example.controller.TransacaoController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.AbstractMap;

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

        //Cria painel de filtros e define um espaçamentos entre os elementos
        JPanel filtrosPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        filtrosPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 5,50));

        //Cria campos de data
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');

            campoDataFiltroInicial = new JFormattedTextField(mascaraData);
            campoDataFiltroFinal = new JFormattedTextField(mascaraData);

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

        //Cria painel para resumos
        JPanel resumoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JLabel totalReceitas = new JLabel("Total Receitas: R$");
        JLabel totalDespesas = new JLabel("Total Despesas: R$");

        resumoPanel.setBorder(BorderFactory.createTitledBorder("Resumo"));
        resumoPanel.add(totalReceitas);
        resumoPanel.add(totalDespesas);

        //Cria cabeçalho para tabela de resultados
        String[] header = {"Tipo", "Data", "Categoria", "Descrição", "Valor"};
        DefaultTableModel model = new DefaultTableModel(header, 0);

        //Instancia a tabela
        JTable resumoTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(resumoTable);

        //Aplica BOLD no cabeçalho da tabela
        Font font_atual = resumoTable.getFont();
        Font negrito = font_atual.deriveFont(Font.BOLD);
        resumoTable.getTableHeader().setFont(negrito);

        //Centraliza o conteudo das celulas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        resumoTable.setDefaultRenderer(Object.class, centerRenderer);

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
                        model,
                        totalReceitas,
                        totalDespesas
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

        JPanel centroPanel = new JPanel(new BorderLayout());
        centroPanel.add(resumoPanel, BorderLayout.NORTH);
        centroPanel.add(scrollPane, BorderLayout.CENTER);

        //Adiciona todos os frames ao painel
        panel.add(filtrosPanel, BorderLayout.NORTH);
        panel.add(centroPanel, BorderLayout.CENTER);
        panel.add(panelBotao, BorderLayout.SOUTH);

        //Adiciona painel ao frame principal
        add(panel);
    }
}
