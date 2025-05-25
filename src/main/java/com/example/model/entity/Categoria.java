package com.example.model.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Conta conta;

    @Column(nullable = false)
    private String categoria;

    public Categoria() {}

    public Categoria(Conta conta, String categoria) {
        this.conta = conta;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return categoria;
    }
}
