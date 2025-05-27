package com.example.view;

import com.example.controller.ContaController;

import javax.swing.*;
import java.awt.*;

public class Cadastro extends JFrame {

    public Cadastro() {

        //Configurações basicas da tela principal
        setTitle("Nova conta");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        //Insere grade para layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Insere titulo de login
        JLabel userLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(userLabel, gbc);

        //Insere campo de login
        JTextField login = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        add(login, gbc);

        //Insere titulo de senha
        JLabel passLabel = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(passLabel, gbc);

        //Insere campo de senha
        JPasswordField senha = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        add(senha, gbc);

        //Insere titulo de confirmação de senha
        JLabel confPassLabel = new JLabel("Confirmar Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(confPassLabel, gbc);

        //Insere campo de confirmação de senha
        JPasswordField confSenha = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        add(confSenha, gbc);

        //Insere botão de cadastrar
        JButton botao = new JButton("Cadastrar");
        botao.setBackground(new Color(0, 120, 215));
        botao.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botao, gbc);

        //Inicia controller de conta
        ContaController c = new ContaController();

        //Define ação do botão de cadastro
        botao.addActionListener(e ->

                //Realiza o cadastro
                c.cadastrar(
                        login.getText().toLowerCase().trim(),
                        new String(senha.getPassword()).toLowerCase().trim(),
                        new String(confSenha.getPassword()).toLowerCase().trim(),
                        this
        ));
    }
}