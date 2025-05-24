package com.example.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class NovaTransacao extends JFrame {

    public NovaTransacao() {

        setTitle("Gestão Financeira - Adicionar Transação");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // título
        JLabel titulo = new JLabel("Adicionar Nova Transação", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // formulário
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Valor: (Apenas números e separe por .)"));
        JTextField campoValor = new JTextField();
        panel.add(campoValor);

        panel.add(new JLabel("tipo:"));
        JComboBox<String> campoTipo = new JComboBox<String>(new String[]{"", "Receita", "Despesa"});
        panel.add(campoTipo);

        ArrayList<String> categorias = new ArrayList<>();

        panel.add(new JLabel("Categoria:"));
        JComboBox<String> campoCategoria = new JComboBox<String>();
        for (String categoria : categorias) {
            campoCategoria.addItem(categoria);
        }
        panel.add(campoCategoria);

        panel.add(new JLabel("Data:"));

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');

            campoData = new JFormattedTextField(mascaraData); // 2. Instanciar dentro do try
            campoData.setColumns(10);

            panel.add(campoData);
        } catch (ParseException er) {
            JOptionPane.showMessageDialog(null, "Erro ao armazenar a data. Tente novamente!");
            return;
        }

        panel.add(new JLabel("Descrição:"));
        JTextField campoDescricao = new JTextField();
        panel.add(campoDescricao);

        telaLancamento.add(panel, BorderLayout.CENTER);

        // botões
        JPanel botoesPanel = new JPanel();

        //Cria botão de salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(0, 168, 107));
        btnSalvar.setForeground(Color.white);

        //Cria botão de cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(215, 60, 60));
        btnCancelar.setForeground(Color.white);

        btnSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(campoValor.getText().isEmpty() ||
                        campoTipo.getSelectedIndex() == 0 ||
                        campoCategoria.getSelectedItem().equals("") ||
                        campoDescricao.getText().isEmpty() ||
                        campoData.getText().contains("_")){

                    if(Main.contaAtiva.getCategorias().isEmpty()) {

                        JOptionPane.showMessageDialog(null, "Não há categorias cadastrada. Adicionar uma categoria!");
                        categorias();
                        return;
                    }

                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(campoValor.getText().matches("\\d+")){

                    // Tenta converter para LocalDate
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    campoValor.setText(campoValor.getText().replace(',', '.'));
                    float valor = Float.parseFloat(campoValor.getText());

                    String selecionado = (String) campoCategoria.getSelectedItem();

                    try {
                        LocalDate dataConvertida = LocalDate.parse(campoData.getText(), formato);

                        if(campoCategoria.getSelectedItem().equals("Receita")){
                            Main.contaAtiva.depositar(valor, dataConvertida, selecionado, campoDescricao.getText());
                        } else {
                            Main.contaAtiva.sacar(valor, dataConvertida, selecionado, campoDescricao.getText());
                        }

                        JOptionPane.showMessageDialog(null,
                                "Transação adicionada com sucesso!",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE);

                        telaLancamento.dispose();
                        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Informe apenas números para Valor!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(ev -> {

            telaLancamento.dispose();
        });

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnCancelar);

        telaLancamento.add(botoesPanel, BorderLayout.SOUTH);

        telaLancamento.setVisible(true);
    }
}
