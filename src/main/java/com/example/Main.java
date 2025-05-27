package com.example;

import com.example.model.entity.Conta;
import com.example.view.Login;

import javax.swing.SwingUtilities;

public class Main {

    //Cria o atributo da conta atual
    public static Conta contaAtiva;

    //Ponto de partida de execução
    public static void main(String[] args){

        //Executa a interface gráfica instanciando a classe Login
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
