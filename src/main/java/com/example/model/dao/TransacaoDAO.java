package com.example.model.dao;

import com.example.model.entity.Transacao;
import org.hibernate.SessionFactory;

import java.util.List;

import static com.example.util.HibernateUtil.getFactory;

public interface TransacaoDAO {

    SessionFactory factory = getFactory();

    boolean salvar(Transacao transacao);
    List<Transacao> getTransacaoByCategoria(String categoria);
    List<Transacao> getTransacoes();

}
