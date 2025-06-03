package com.example.model.entity;

import com.example.Main;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_transacao;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conta", nullable = false)
    private Conta conta;

    public Transacao() {}

    public Transacao(float valor, String categoria, String descricao, LocalDate data, String tipo) {
        this.tipo = tipo;
        this.valor = valor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.data = data;
        this.conta = Main.contaAtiva;
    }

    public String getTipo() {
        return tipo;
    }

    public Float getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {return descricao;}

    public LocalDate getData() {
        return data;
    }

    public void setCategoria(String categoria) {}
}
