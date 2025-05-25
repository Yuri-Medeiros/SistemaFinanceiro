package com.example.model.impl;

import com.example.model.dao.CategoriaDAO;
import com.example.model.entity.Categoria;
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
    public boolean editar(Categoria categoria) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
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
    public boolean excluir(Categoria categoria) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            session.remove(categoria);
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
}
