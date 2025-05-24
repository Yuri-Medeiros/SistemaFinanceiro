package com.example;

import com.example.model.entity.Transacao;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Conta extends JFrame{

    private final String login;
    private final String senha;
    private float saldo;

    public Conta(String login, String senha) {
        this.saldo = 0;
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() { return this.login; }

    public String getSenha() { return this.senha; }

    public List<String> getCategorias() { return this.categorias; }

    public float getSaldo() { return saldo; }

    public List<Transacao> getExtrato() { return this.extrato; }

    public void depositar(float valor, LocalDate data, String categoria, String descricao){
        Transacao deposito = new Transacao(valor, categoria, descricao, data, 'E');
        this.extrato.add(deposito);
        this.saldo += valor;
    }

    public void sacar(float valor, LocalDate data, String categoria, String descricao){
        Transacao saque = new Transacao(valor, categoria, descricao, data, 'S');
        this.extrato.add(saque);
        this.saldo -= valor;
    }

    public void adicionarCategoria(String categoria) { this.categorias.add(categoria); }

    public void editarCategoria(String categoriaOld, String categoriaNew) {

        this.categorias.remove(categoriaOld);
        this.categorias.add(categoriaNew);
    }

    public void removerCategoria(String categoria) {

        this.categorias.remove(categoria);
    }

    public Map<Character, Float> mostrarSaldoPorCategoria(LocalDate inicio, LocalDate fim){

        Map<Character, Float> valorTotal = new HashMap<>();
        valorTotal.put('E', 0f);
        valorTotal.put('S', 0f);

        for(Transacao transacao : this.extrato){

            boolean validaData = ((transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim))
                    || transacao.getData().equals(inicio)
                    || transacao.getData().equals(fim));

            if(validaData){

                Character tipo = transacao.getTipo();
                float valor = transacao.getValor() + valorTotal.get(tipo);

                valorTotal.put(tipo, valor);
            }
        }

        return valorTotal;
    }
}
