package com.example.model.impl;

import com.example.model.dao.CategoriaDAO;
import com.example.model.entity.Categoria;
import com.example.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;


public class CategoriaSQLite implements CategoriaDAO {

    @Override
    public boolean salvar(Categoria categoria) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(categoria);
            tx.commit();

        } catch (ConstraintViolationException e) {
            if (tx != null) tx.rollback();

            throw e;
        } catch (Exception e) {
            if (tx != null) tx.rollback();

            return false;
        }

        return true;
    }

    @Override
    public boolean editar(String oldCategoria, String newCategoria) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();

            Categoria categoria = getCategoria(oldCategoria);

            if (categoria != null) {
                categoria.setCategoria(newCategoria);
            }

            session.merge(categoria);
            tx.commit();

        } catch (ConstraintViolationException e) {
            if (tx != null) tx.rollback();

            throw e;
        } catch (Exception e) {
            if (tx != null) tx.rollback();

            return false;
        }

        return true;
    }

    @Override
    public boolean excluir(String categoria) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Categoria xCategoria = getCategoria(categoria);

            xCategoria = session.merge(xCategoria);

            session.remove(xCategoria);
            tx.commit();

        } catch (ConstraintViolationException e) {
            if (tx != null) tx.rollback();

            throw e;
        } catch (Exception e) {
            if (tx != null) tx.rollback();

            return false;
        }

        return true;
    }

    public Categoria getCategoria(String categoria) {

        try (Session session = factory.openSession()) {

            return session.createQuery(
                            "from Categoria where conta = :conta and categoria = :categoria", Categoria.class)
                    .setParameter("conta", Main.contaAtiva)
                    .setParameter("categoria", categoria)
                    .uniqueResult();
        }
    }
}
