package com.example.model.impl;

import com.example.model.dao.TransacaoDAO;
import com.example.model.entity.Transacao;

import jakarta.transaction.Transaction;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;


public class TransacaoSQLite implements TransacaoDAO {

    public boolean salvar(Transacao transacao) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            session.beginTransaction();
            session.persist(transacao);
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
