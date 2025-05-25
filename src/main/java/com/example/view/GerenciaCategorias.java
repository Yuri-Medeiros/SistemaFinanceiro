package com.example.view;

import com.example.controller.CategoriaController;
import com.example.model.entity.Categoria;

import javax.swing.*;
import java.awt.*;

import static com.example.util.CriarBotao.criarBotao;

public class GerenciaCategorias extends JFrame {

    private final CategoriaController c = new CategoriaController();

    public GerenciaCategorias() {

        //Configurações basicas da tela principal
        setTitle("Gerenciar Categorias");
        setSize(400, 300);
        setLocationRelativeTo(null);

        //Cria e configura fram principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Cria painal de botões
        JPanel panelBotao = new JPanel();

        //Cria lista de categorias para exibição
        //TODO: Adicionar categorias
        JList<String> categorias = new JList<>();
        DefaultListModel<String> listModel = new DefaultListModel<>();

        //Cria e configura botão de adicionar
        JButton btnAdicionar = criarBotao("Adicionar", new Color(0, 168, 107));
        btnAdicionar.addActionListener(e -> c.cadastrarCategoria(this));

        //Cria e configura botão de editar
        JButton btnEditar = criarBotao("Editar", new Color(0, 120, 215));
        btnEditar.addActionListener(er -> c.editarCategoria(this));

        //Cria e configura botão de remover
        JButton btnRemover = criarBotao("Remover", new Color(215, 60, 60));
        btnRemover.addActionListener(err -> c.excluirCategoria(this));

        //Adiciona botões ao painel de botões
        panelBotao.add(btnAdicionar);
        panelBotao.add(btnEditar);
        panelBotao.add(btnRemover);

        //Cria painel de exibição de categorias
        JScrollPane scrollPane = new JScrollPane(categorias);

        //Adiciona paineis ao frame principal
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotao, BorderLayout.SOUTH);

        //Adiciona o frame principal a tela principal
        add(panel);
    }
}
