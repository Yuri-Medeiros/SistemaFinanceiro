package com.example.model.impl;

import com.example.model.dao.CategoriaDAO;
import com.example.model.dao.TransacaoDAO;
import com.example.model.entity.Categoria;
import com.example.Main;
import com.example.model.entity.Transacao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class CategoriaSQLite implements CategoriaDAO {

    @Override
    public void salvar(Categoria categoria) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(categoria);
            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();

            throw e;
        }
    }

    @Override
    public void editar(String oldCategoria, String newCategoria) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();

            Categoria categoria = getCategoria(oldCategoria);

            if (categoria != null) {
                categoria.setCategoria(newCategoria);
                session.merge(categoria);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();

            throw e;
        }
    }

    @Override
    public void excluir(String categoria) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Categoria xCategoria = session.createQuery(
                            "from Categoria where conta = :conta and categoria = :categoria", Categoria.class)
                    .setParameter("conta", Main.contaAtiva)
                    .setParameter("categoria", categoria)
                    .uniqueResult();

            session.remove(xCategoria);
            tx.commit();

        } catch (Exception e) {
            //if (tx != null) tx.rollback();
            //throw e;
            System.out.println(e.getMessage());

        }
    }

    @Override
    public Categoria getCategoria(String categoria) {

        try (Session session = factory.openSession()) {

            return session.createQuery(
                            "from Categoria where conta = :conta and categoria = :categoria", Categoria.class)
                    .setParameter("conta", Main.contaAtiva)
                    .setParameter("categoria", categoria)
                    .uniqueResult();
        }
    }

    @Override
    public List<Categoria> getCategorias() {

        try (Session session = factory.openSession()) {

            return session.createQuery(
                            "from Categoria where conta = :conta", Categoria.class)
                    .setParameter("conta", Main.contaAtiva)
                    .list();
        }
    }

}
