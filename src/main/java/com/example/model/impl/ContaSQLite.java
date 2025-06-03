package com.example.model.impl;

import com.example.model.dao.ContaDAO;
import com.example.model.entity.Categoria;
import com.example.model.entity.Conta;
import com.example.model.entity.Transacao;
import com.example.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.util.HibernateUtil;



import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class ContaSQLite implements ContaDAO {

    @Override
    public void logar(String login, String senha){

        try (Session session = factory.openSession()) {

            Conta conta = session.createQuery(
                    "from Conta where login = :login and senha = :senha", Conta.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .uniqueResult();

            if (conta == null) {
                throw new  IllegalArgumentException("Login ou senha incorretos");
            }

            Main.contaAtiva = conta;
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
    public void setSaldo(float valor, String tipo){

        Transaction tx = null;

        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();

            if (tipo.equals("Receita")) {

                Main.contaAtiva.setSaldo(valor);
            } else {

                Main.contaAtiva.setSaldo((valor * -1));
            }

            session.merge(Main.contaAtiva);
            tx.commit();

        }  catch (Exception e) {
            if (tx != null) tx.rollback();

            throw e;
        }
    }

    @Override
    public boolean loginExiste(String login) {
        try (org.hibernate.Session session = HibernateUtil.getFactory().openSession()) {
            String hql = "SELECT COUNT(*) FROM Conta WHERE login = :login";
            Long count = (Long) session.createQuery(hql)
                    .setParameter("login", login)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
