package com.example.model.impl;

import com.example.model.dao.TransacaoDAO;
import com.example.model.entity.Categoria;
import com.example.model.entity.Transacao;

import com.example.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import javax.management.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class TransacaoSQLite implements TransacaoDAO {

    @Override
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

    @Override
    public List<Transacao> getTransacaoByCategoria(String categoria) {

        try (Session session = factory.openSession()) {

            Categoria categoriaObj = session.createQuery(
                            "from Categoria where conta = :conta and categoria = :categoria", Categoria.class)
                    .setParameter("conta", Main.contaAtiva)
                    .setParameter("categoria", categoria)
                    .uniqueResult();

            List<Transacao> transacoes = session.createQuery(
                            "from Transacao where categoria = :categoria and conta = :conta", Transacao.class)
                    .setParameter("categoria", categoriaObj)
                    .setParameter("conta", Main.contaAtiva)
                    .list();

            if (transacoes.isEmpty()) return null;

            return transacoes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Transacao> getTransacoesConsulta(LocalDate dataInicio, LocalDate dataFim, String tipo){

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();

            if (tipo.equals("Todos")) {

                return session.createQuery(
                                "from Transacao where data between :dtInicio and :dtFinal",
                                Transacao.class)
                        .setParameter("dtInicio", dataInicio)
                        .setParameter("dtFinal", dataFim)
                        .list();
            }

            return session.createQuery(
            "from Transacao where tipo = :tipo and data between :dtInicio and :dtFinal",
            Transacao.class)
                    .setParameter("tipo", tipo)
                    .setParameter("dtInicio", dataInicio)
                    .setParameter("dtFinal", dataFim)
                    .list();
        }
    }
}
