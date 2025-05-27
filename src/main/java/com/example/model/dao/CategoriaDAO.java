package com.example.model.dao;

import com.example.model.entity.Categoria;
import org.hibernate.SessionFactory;

import static com.example.util.HibernateUtil.getFactory;

public interface CategoriaDAO {

    SessionFactory factory = getFactory();

    boolean salvar(Categoria categoria);
    boolean editar(String oldCategoria, String Categoria);
    boolean excluir(String categoria);
    Categoria getCategoria(String categoria);
}
