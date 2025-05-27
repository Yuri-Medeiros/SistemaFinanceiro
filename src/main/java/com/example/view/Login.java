package com.example.view;

import com.example.controller.ContaController;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    public Login() {

        //Configurações basicas da tela principal
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        //Insersão de grade para layout na tela
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Insere titulo do campo de usuario
        JLabel userLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(userLabel, gbc);

        //Insere campo de usuario
        JTextField login = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        add(login, gbc);

        //Insere titulo do campo de senha
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

        //Insere botão de login
        JButton botaoLogin = new JButton("Login");
        botaoLogin.setBackground(new Color(0, 168, 107));
        botaoLogin.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botaoLogin, gbc);

        //Insere botão de cadastro
        JButton botaoCadastro = new JButton("Cadastrar");
        botaoCadastro.setBackground(new Color(0, 120, 215));
        botaoCadastro.setForeground(Color.WHITE);
        gbc.gridy = 3;
        add(botaoCadastro, gbc);

        //Inicia controller de conta
        ContaController c = new ContaController();

        //Define ação para botão de login
        botaoLogin.addActionListener(e ->

                //Efetiva o login
                c.logar(login.getText().toLowerCase().trim(),
                        new String(senha.getPassword()).toLowerCase().trim(),
                        this)
        );

        //Define ação para botão de cadastro
        botaoCadastro.addActionListener(e -> {

            //Inicia tela de Cadastro
            SwingUtilities.invokeLater(() -> new Cadastro().setVisible(true));
        });
    }
}