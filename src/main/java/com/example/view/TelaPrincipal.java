package com.example.view;

import com.example.Main;
import com.example.model.dao.ContaDAO;
import com.example.model.impl.ContaSQLite;

import javax.swing.*;
import java.awt.*;

import static com.example.util.CriarBotao.criarBotao;


public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        //Configurações basicas do frame principal
        setTitle("Gestão Financeira Pessoal");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        //Cria painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Gestão Financeira Pessoal", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        //Obtem o saldo atual da conta
        String saldo = "Saldo atual: R$" + Main.contaAtiva.getSaldo();

        //Insere o saldo no painel principal
        JLabel saldoLabel = new JLabel(saldo, SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(saldoLabel, BorderLayout.CENTER);

        //Cria painel de botões e botões
        JPanel panelBotao = new JPanel(new GridLayout(1, 4, 10, 0));
        JButton btnAdicionar = criarBotao("Adicionar Transação", new Color(0, 168, 107));
        JButton btnConsulta = criarBotao("Consultar", new Color(0, 120, 215));
        JButton btnCategorias = criarBotao("Categorias", new Color(255, 140, 0));
        JButton btnSair = criarBotao("Logout", new Color(215, 60, 60));

        //Adiciona botões no painel e painel no frame principal
        panelBotao.add(btnAdicionar);
        panelBotao.add(btnConsulta);
        panelBotao.add(btnCategorias);
        panelBotao.add(btnSair);
        panel.add(panelBotao, BorderLayout.SOUTH);

        //Configura ação do botão de adicionar transação
        btnAdicionar.addActionListener(e -> {

            ContaDAO SQLite = new ContaSQLite();

            //Verifica se não há categorias cadastrada e abre gerenciamento de categorias
            if(SQLite.getCategorias().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Não há categorias cadastrada. Adicionar uma categoria!");
                SwingUtilities.invokeLater(() -> new GerenciaCategorias().setVisible(true));
                return;
            }

            dispose();
            SwingUtilities.invokeLater(() -> new NovaTransacao().setVisible(true));
        });

        //Configura ação do botão de consultar transações
        btnConsulta.addActionListener(e -> {

            dispose();
            SwingUtilities.invokeLater(() -> new Consulta().setVisible(true));
        });

        //Configura ação do botão de gerencisr categorias
        btnCategorias.addActionListener(e -> {

            SwingUtilities.invokeLater(() -> new GerenciaCategorias().setVisible(true));
        });

        //Configura ação do botão de sair
        btnSair.addActionListener(e -> {

            Main.contaAtiva = null;
            dispose();
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        });

        //Adiciona frame principal a tela principal
        add(panel);
    }
}
