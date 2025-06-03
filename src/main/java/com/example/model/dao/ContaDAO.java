package com.example.model.dao;

import com.example.model.entity.Conta;
import org.hibernate.SessionFactory;


import static com.example.util.HibernateUtil.getFactory;

public interface ContaDAO {

    SessionFactory factory = getFactory();

    void logar(String login, String senha);
    boolean salvar(Conta conta);
    boolean loginExiste(String login);
    void setSaldo(float saldo, String tipo);

}
