package com.example.view;

import com.example.controller.CategoriaController;
import com.example.controller.TransacaoController;
import com.example.model.entity.Categoria;
import com.example.model.impl.ContaSQLite;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.List;
import java.text.ParseException;

public class NovaTransacao extends JFrame {

    private JFormattedTextField campoData = null;
    private final CategoriaController g = new CategoriaController();
    private final TransacaoController t = new TransacaoController();
    private final ContaSQLite SQLite = new ContaSQLite();

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

        List<Categoria> categorias = SQLite.getCategorias(Main.contaAtiva);

        panel.add(new JLabel("Categoria:"));
        JComboBox<String> campoCategoria = new JComboBox<String>();
        for (Categoria categoria : categorias) {
            campoCategoria.addItem(categoria.toString());
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

        add(panel, BorderLayout.CENTER);

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

        btnSalvar.addActionListener(e -> {t.adicionarTransacao(
                campoValor.getText().toLowerCase().trim(),
                (String) campoCategoria.getSelectedItem(),
                campoDescricao.getText().toLowerCase().trim(),
                (String) campoTipo.getSelectedItem(),
                campoData.getText().toLowerCase().trim(),
                this);});

        btnCancelar.addActionListener(ev -> {

            dispose();
        });

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnCancelar);

        add(botoesPanel, BorderLayout.SOUTH);

        if(SQLite.getCategorias(Main.contaAtiva).isEmpty()) {

            JOptionPane.showMessageDialog(null, "Não há categorias cadastrada. Adicionar uma categoria!");
            SwingUtilities.invokeLater(() -> new GerenciaCategorias().setVisible(true));
        }
    }
}
