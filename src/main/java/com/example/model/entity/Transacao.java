package com.example.model.entity;

import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDate;

@Entity
@Table(name = "Transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Character tipo;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Conta conta;

    public Transacao() {}

    public Transacao(float valor, String categoria, String descricao, LocalDate data, Character tipo, Conta conta) {
        this.tipo = tipo;
        this.valor = valor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.data = data;
        this.conta = conta;
    }

    public Character getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {return descricao;}

    public LocalDate getData() {
        return data;
    }

    public Float getValor() {
        return valor;
    }
}
