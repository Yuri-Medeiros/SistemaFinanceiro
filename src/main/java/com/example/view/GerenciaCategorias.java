package com.example.view;

import com.example.controller.CategoriaController;
import com.example.model.entity.Categoria;
import com.example.model.impl.ContaSQLite;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        ContaSQLite SQLite = new ContaSQLite();
        List<Categoria> dbCategorias = SQLite.getCategorias();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Categoria categoria : dbCategorias) {
            listModel.addElement(categoria.getCategoria());
        }

        //Configura o campo de categorias
        JList<String> categorias = new JList<>();
        categorias.setModel(listModel);
        categorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(categorias);

        //Cria botões
        JButton btnAdicionar = criarBotao("Adicionar", new Color(0, 168, 107));
        JButton btnEditar = criarBotao("Editar", new Color(0, 120, 215));
        JButton btnRemover = criarBotao("Remover", new Color(215, 60, 60));

        //Adiciona botões ao painel de botões
        panelBotao.add(btnAdicionar);
        panelBotao.add(btnEditar);
        panelBotao.add(btnRemover);

        //Adiciona paineis ao frame principal
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotao, BorderLayout.SOUTH);

        //Cria e configura botão de adicionar
        btnAdicionar.addActionListener(e -> c.cadastrarCategoria(this));

        //Cria e configura botão de editar
        btnEditar.addActionListener(e -> c.editarCategoria(categorias.getSelectedValue(), this));

        //Cria e configura botão de remover
        btnRemover.addActionListener(e -> c.excluirCategoria(categorias.getSelectedValue(), this));

        //Adiciona o frame principal a tela principal
        add(panel);
    }
}
