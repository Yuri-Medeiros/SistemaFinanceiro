package com.example.model.impl;

import com.example.model.dao.ContaDAO;
import com.example.model.entity.Categoria;
import com.example.model.entity.Conta;
import com.example.model.entity.Transacao;
import com.example.view.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class ContaSQLite implements ContaDAO {

    @Override
    public void logar(String login, String senha){

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            List<Conta> contas = session.createQuery(
                    "from Conta where login = :login and senha = :senha", Conta.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .list();

            if (contas.isEmpty()) {
                throw new  IllegalArgumentException("Login ou senha incorretos");
            }

            Main.contaAtiva = contas.getFirst();
        }
    }

    @Override
    public boolean salvar(Conta conta) {
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(conta);
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
    public List<Categoria> getCategorias(Conta conta) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            List<Categoria> categorias = session.createQuery(
                            "from Categoria where conta = :conta", Categoria.class)
                    .setParameter("conta", conta)
                    .list();

            if (categorias.isEmpty()) {
                throw new  IllegalArgumentException("Não possui categorias");
            }

            return categorias;
        }
    }

    @Override
    public List<Transacao> getTransacoes(Conta conta) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            List<Transacao> transacaos = session.createQuery(
                            "from Transacao where conta = :conta", Transacao.class)
                    .setParameter("conta", conta)
                    .list();

            if (transacaos.isEmpty()) {
                throw new  IllegalArgumentException("Não possui transacoes");
            }

            return transacaos;
        }
    }
}
