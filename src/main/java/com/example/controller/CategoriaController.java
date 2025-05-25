package com.example.controller;

import com.example.model.entity.Categoria;
import com.example.model.impl.CategoriaSQLite;
import com.example.view.GerenciaCategorias;
import com.example.view.Main;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import java.awt.*;

public class CategoriaController extends JFrame {

    final CategoriaSQLite SQLite = new CategoriaSQLite();

    public void cadastrarCategoria(GerenciaCategorias view) {

        //Variavel de auxilio
        String novaCategoria;

        //Looping para garantir valores validos
        do {

            //Obtem a categoria
            novaCategoria = JOptionPane.showInputDialog(this, "Digite o nome da nova categoria:");

            //Verifica se é valida
            if(novaCategoria == null){
                return;
            }

            //Leve formatada na categoria
            novaCategoria = novaCategoria.trim();

        } while (novaCategoria.isEmpty());

        try {

            //Instancia entidade de categoria
            Categoria categoria = new Categoria(Main.contaAtiva, novaCategoria);

            //Exibe erro para excessões genericas
            if (!SQLite.salvar(categoria)) {
                JOptionPane.showMessageDialog(CategoriaController.this, "Não foi possivel salvar a categoria. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

            //Atualiza a pagina com a lista nova
            view.dispose();
            SwingUtilities.invokeLater(() -> new GerenciaCategorias().setVisible(true));
        } catch (ConstraintViolationException e) {
            //Exibi erro de categoria ja cadastrada
            JOptionPane.showMessageDialog(CategoriaController.this, "Categoria ja existe! Informe outro", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void editarCategoria(
            Categoria categoria,
            GerenciaCategorias view) {

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
    }

    public void excluirCategoria(GerenciaCategorias view) {

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
    }
}
