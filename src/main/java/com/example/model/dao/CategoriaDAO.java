package com.example.model.dao;

import com.example.model.entity.Categoria;
import org.hibernate.SessionFactory;

import static com.example.util.HibernateUtil.getFactory;

public interface CategoriaDAO {

    SessionFactory factory = getFactory();

    boolean salvar(Categoria categoria);
    boolean editar(Categoria categoria);
    boolean excluir(Categoria categoria);
}
