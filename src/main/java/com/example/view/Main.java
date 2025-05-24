package com.example.view;

import com.example.model.entity.Conta;

import javax.swing.SwingUtilities;
import java.util.ArrayList;

public class Main {

    //Cria os atributos de controle de usuarios
    public static final ArrayList<Conta> contasCadastradas = new ArrayList<>();
    public static Conta contaAtiva;

    //Ponto de partida de execução
    public static void main(String[] args){

        //Executa a interface gráfica chamando criando a classe Login
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));

    }
}


