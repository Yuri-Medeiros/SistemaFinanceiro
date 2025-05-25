package com.example.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private float saldo;

    public Conta(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.saldo = 0;
    }

    public Conta() {}

    public float getSaldo() {
        return saldo;
    }

    public List<Transacao> getTransacoes() {
        return new ArrayList<>();
    }

    public List<Categoria> getCategorias() {
        return new ArrayList<>();
    }
}
