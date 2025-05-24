package com.example.view;

import com.example.controller.TransacaoController;
import com.example.model.entity.Transacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class TelaPrincipal extends JFrame {

    private JFormattedTextField campoData = null;
    private JFormattedTextField campoDataFiltroInicial = null;
    private JFormattedTextField campoDataFiltroFinal = null;
    private TransacaoController t = new TransacaoController();

    public TelaPrincipal() {
        setTitle("Gestão Financeira Pessoal");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Criando o painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Criando o título
        JLabel titulo = new JLabel("Gestão Financeira Pessoal", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        String saldo = "Saldo atual: R$" + Main.contaAtiva.getSaldo();

        // Exibição do saldo
        JLabel saldoLabel = new JLabel(saldo, SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(saldoLabel, BorderLayout.CENTER);

        // Criando botões
        JPanel panelBotao = new JPanel(new GridLayout(1, 4, 10, 0));
        JButton btnAdicionar = criarBotao("Adicionar Transação", new Color(0, 168, 107));
        JButton btnConsulta = criarBotao("Consultar", new Color(0, 120, 215));
        JButton btnCategorias = criarBotao("Categorias", new Color(255, 140, 0));
        JButton btnSair = criarBotao("Logout", new Color(215, 60, 60));

        panelBotao.add(btnAdicionar);
        panelBotao.add(btnConsulta);
        panelBotao.add(btnCategorias);
        panelBotao.add(btnSair);
        panel.add(panelBotao, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {

            t.abrirTransacao();
        });

        btnConsulta.addActionListener(e -> {

            consulta();
        });

        btnCategorias.addActionListener(e -> {

            categorias();
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.contaAtiva = null;
                dispose();
                SwingUtilities.invokeLater(() -> new Login().setVisible(true));
            }
        });

        // Adicionando o painel principal à janela
        add(panel);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);

        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        return botao;
    }

    private void novaTransacao(){

        SwingUtilities.invokeLater(() -> new NovaTransacao().setVisible(true));

        JFrame telaLancamento = new JFrame();
        telaLancamento.setVisible(true);


    }

    private void consulta() {
        JFrame telaConsulta = new JFrame();
        telaConsulta.setVisible(true);

        telaConsulta.setTitle("Extrato");
        telaConsulta.setSize(600, 400);
        telaConsulta.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel filtrosPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Filtros
        panel.add(new JLabel("Data inicial:"));
        panel.add(new JLabel("Data final:"));

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

        JLabel tipoLabel = new JLabel("Tipo:");
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Todos", "Receita", "Despesa"});

        filtrosPanel.add(new JLabel("Data inicial:"));
        filtrosPanel.add(campoDataFiltroInicial);
        filtrosPanel.add(new JLabel("Data final:"));
        filtrosPanel.add(campoDataFiltroFinal);
        filtrosPanel.add(tipoLabel);
        filtrosPanel.add(tipoCombo);

        // Área de texto para resultados
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // Botão de consulta
        JButton consultarButton = criarBotao("Consultar", new Color(0, 120, 215));
        consultarButton.addActionListener(e -> {
            try {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inicio = campoDataFiltroInicial.getText().isEmpty() ? LocalDate.MIN : LocalDate.parse(campoDataFiltroInicial.getText(), formatter);
                LocalDate fim = campoDataFiltroFinal.getText().isEmpty() ? LocalDate.MAX : LocalDate.parse(campoDataFiltroFinal.getText(), formatter);

                String tipoSelecionado = (String) tipoCombo.getSelectedItem();
                List<Character> tipos = new ArrayList<>();
                if (tipoSelecionado.equals("Todos") || tipoSelecionado.equals("Receita")) {
                    tipos.add('E');
                }
                if (tipoSelecionado.equals("Todos") || tipoSelecionado.equals("Despesa")) {
                    tipos.add('S');
                }

                resultadoArea.setText("Data | Categoria | Descrição | Valor\n");
                for (Transacao transacao : Main.contaAtiva.getExtrato()) {
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
        });

        JButton cancelButton = criarBotao("Cancelar", new Color(215, 60, 60));
        cancelButton.addActionListener(e -> {

            telaConsulta.dispose();
        });

        JPanel panelBotao = new JPanel();
        panelBotao.add(consultarButton);
        panelBotao.add(cancelButton);

        panel.add(filtrosPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotao, BorderLayout.SOUTH);

        telaConsulta.add(panel);
    }

    private void categorias() {

        JFrame telaCategorias = new JFrame();
        telaCategorias.setVisible(true);

        telaCategorias.setTitle("Gerenciar Categorias");
        telaCategorias.setSize(400, 300);
        telaCategorias.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBotao = new JPanel();

        JList<String> categorias = new JList<>(Main.contaAtiva.getCategorias().toArray(new String[0]));
        DefaultListModel<String> listModel = new DefaultListModel<>();

        JButton btnAdicionar = criarBotao("Adicionar", new Color(0, 168, 107));
        btnAdicionar.addActionListener(e -> {

            String novaCategoria = "";

            do {

                novaCategoria = JOptionPane.showInputDialog(this, "Digite o nome da nova categoria:");

                if(novaCategoria == null){
                    return;
                }

                novaCategoria = novaCategoria.trim();

            } while (novaCategoria.isEmpty());

            Main.contaAtiva.adicionarCategoria(novaCategoria);

            telaCategorias.dispose();
            categorias();

        });

        JButton btnEditar = criarBotao("Editar", new Color(0, 120, 215));
        btnEditar.addActionListener(er -> {

            int selectedIndex = categorias.getSelectedIndex();

            if (selectedIndex != -1) {

                String categoriaAtual = categorias.getSelectedValue();
                String novaCategoria;

                do {

                    novaCategoria = JOptionPane.showInputDialog(this, "Editar categoria:", categoriaAtual);

                    if (novaCategoria == null) {
                        return;
                    }

                    novaCategoria = novaCategoria.trim();

                } while (novaCategoria.isEmpty());

                if (!novaCategoria.equals(categoriaAtual)) {

                    Main.contaAtiva.editarCategoria(categorias.getSelectedValue(), novaCategoria.trim());

                    listModel.clear();
                    Main.contaAtiva.getCategorias().forEach(listModel::addElement);

                    telaCategorias.dispose();
                    categorias();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton btnRemover = criarBotao("Remover", new Color(215, 60, 60));
        btnRemover.addActionListener(err -> {

            int selectedIndex = categorias.getSelectedIndex();

            if (selectedIndex != -1) {

                try {
                    Main.contaAtiva.removerCategoria(categorias.getSelectedValue());

                    listModel.clear();
                    Main.contaAtiva.getCategorias().forEach(listModel::addElement);

                    telaCategorias.dispose();
                    categorias();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria para remover", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        panelBotao.add(btnAdicionar);
        panelBotao.add(btnEditar);
        panelBotao.add(btnRemover);

        // Lista de categorias
        JScrollPane scrollPane = new JScrollPane(categorias);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(panelBotao, BorderLayout.SOUTH);

        telaCategorias.add(panel);
    }
}
