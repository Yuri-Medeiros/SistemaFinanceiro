package com.example;

import javax.swing.*;
import java.time.LocalDate;

public class Transacao extends JFrame {

    private final Character tipo;
    private final float valor;
    private final String categoria;
    private final String descricao;
    private final LocalDate data;

    public Transacao(float valor, String categoria, String descricao, LocalDate data, Character tipo) {
        this.tipo = tipo;
        this.valor = valor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.data = data;
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
