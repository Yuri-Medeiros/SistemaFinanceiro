package com.example.model.dao;

import com.example.model.entity.Conta;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public interface ContaDAO {
    SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public void logar(String login, String senha);
    public boolean salvar(Conta conta);
}
