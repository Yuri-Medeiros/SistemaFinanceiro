package com.example.controller;

import com.example.model.dao.CategoriaDAO;
import com.example.model.dao.TransacaoDAO;
import com.example.model.entity.Categoria;
import com.example.model.entity.Transacao;
import com.example.model.impl.CategoriaSQLite;
import com.example.model.impl.TransacaoSQLite;
import com.example.view.Consulta;
import com.example.view.GerenciaCategorias;
import com.example.Main;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import java.util.List;

public class CategoriaController extends JFrame {

    final CategoriaDAO categoriaSQLite = new CategoriaSQLite();
    final TransacaoDAO transacaoSQLite = new TransacaoSQLite();

    public void cadastrarCategoria(GerenciaCategorias view) {

        //Variavel de auxilio
        String novaCategoria;

        do {
            //Obtem a categoria
            novaCategoria = JOptionPane.showInputDialog(this, "Digite o nome da nova categoria:");

            //Verifica se é valida
            if(novaCategoria == null){
                return;
            }

            //Leve formatada na categoria
            novaCategoria = novaCategoria.trim();

        //Looping para garantir valores validos
        } while (novaCategoria.isEmpty());

        try {
            //Instancia entidade de categoria
            Categoria categoria = new Categoria(Main.contaAtiva, novaCategoria);

            //Exibe erro para excessões genericas
            if (!categoriaSQLite.salvar(categoria)) {
                JOptionPane.showMessageDialog(CategoriaController.this, "Não foi possivel salvar a categoria. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Atualiza a pagina com a lista nova
            view.dispose();
            SwingUtilities.invokeLater(() -> new GerenciaCategorias().setVisible(true));

        } catch (ConstraintViolationException e) {
            //Exibi erro de categoria ja cadastrada
            JOptionPane.showMessageDialog(CategoriaController.this, "Categoria ja existe! Informe outro", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editarCategoria(String categoria, GerenciaCategorias view) {

        //Exibe mensagem caso nada tenha sido selecionado
        if (categoria == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Faz o scan da nova descrição da categoria
        String novaCategoria;
        do {
            //Obtem a nova descrição da categoria
            novaCategoria = JOptionPane.showInputDialog(this, "Editar categoria:", categoria);
            novaCategoria = novaCategoria.trim();

            //Looping para garantir valores validos
        } while (novaCategoria.isEmpty() || !novaCategoria.equals(categoria));

        try {

            categoriaSQLite.editar(categoria, novaCategoria);
            view.dispose();
            SwingUtilities.invokeLater(() -> new GerenciaCategorias().setVisible(true));

        } catch (ConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Categoria ja existe! Informe outro", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void excluirCategoria(String categoria, GerenciaCategorias view) {

        //Verifica se foi selecionado alguma categoria
        if (categoria == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            //Busca transacoes com esta categoria
            List<Transacao> transacoes = transacaoSQLite.getTransacaoByCategoria(categoria);

            //Verifica se há transacoes nesta categoria
            if (!transacoes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Há transações nesta categoria. Por favor, altere!", "Erro", JOptionPane.ERROR_MESSAGE);
                view.dispose();
                SwingUtilities.invokeLater(() -> new Consulta().setVisible(true));
            }

            //Exclui a categoria
            if (!categoriaSQLite.excluir(categoria)) {
                JOptionPane.showMessageDialog(this, "Não foi possivel excluir a categoria. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Atualiza a pagina de gerenciamento de categorias
            view.dispose();
            SwingUtilities.invokeLater(() -> new GerenciaCategorias().setVisible(true));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Não foi possivel editar esta categoria. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
