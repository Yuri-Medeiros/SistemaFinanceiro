package com.example.model.dao;

import com.example.model.entity.Categoria;
import org.hibernate.SessionFactory;

import java.util.List;

import static com.example.util.HibernateUtil.getFactory;

public interface CategoriaDAO {

    SessionFactory factory = getFactory();

    void salvar(Categoria categoria);
    void editar(String oldCategoria, String Categoria);
    void excluir(String categoria);
    Categoria getCategoria(String categoria);
    List<Categoria> getCategorias();

}
