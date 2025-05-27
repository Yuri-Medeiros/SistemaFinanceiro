package com.example.model.impl;

import com.example.model.dao.TransacaoDAO;
import com.example.model.entity.Transacao;

import com.example.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;


public class TransacaoSQLite implements TransacaoDAO {

    public boolean salvar(Transacao transacao) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
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

    public List<Transacao> getTransacaoByCategoria(String categoria) {

        try (Session session = factory.openSession()) {

            List<Transacao> transacoes = session.createQuery(
                            "from Transacao where categoria = :categoria and conta = :conta", Transacao.class)
                    .setParameter("categoria", categoria)
                    .setParameter("conta", Main.contaAtiva)
                    .list();

            if (transacoes.isEmpty()) return null;

            return transacoes;
        } catch (Exception e) {
            return null;
        }
    }
}
