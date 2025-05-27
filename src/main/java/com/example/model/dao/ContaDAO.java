package com.example.model.dao;

import com.example.model.entity.Categoria;
import com.example.model.entity.Conta;
import com.example.model.entity.Transacao;
import org.hibernate.SessionFactory;

import java.util.List;

import static com.example.util.HibernateUtil.getFactory;

public interface ContaDAO {

    SessionFactory factory = getFactory();

    void logar(String login, String senha);
    boolean salvar(Conta conta);
    List<Categoria> getCategorias();
    List<Transacao> getTransacoes();
}
