package com.example.view;

import com.example.controller.ContaController;

import javax.swing.*;
import java.awt.*;

public class Cadastro extends JFrame {

    /*
    Define o campo para preencher oo usuario,
    campo para preencher a senha,
    botão para ação
    e o controller
    */
    private final JTextField login;
    private final JPasswordField senha;
    private final JPasswordField confSenha;
    private final ContaController c = new ContaController();

    public Cadastro() {

        //Mesmos elementos abaixo de Login
        setTitle("Nova conta");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(userLabel, gbc);

        login = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        add(login, gbc);

        JLabel passLabel = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(passLabel, gbc);

        senha = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        add(senha, gbc);

        JLabel confPassLabel = new JLabel("Confirmar Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(confPassLabel, gbc);

        confSenha = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        add(confSenha, gbc);

        JButton botao = new JButton("Cadastrar");
        botao.setBackground(new Color(0, 120, 215));
        botao.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botao, gbc);

        botao.addActionListener(
                c.cadastrar(
                        login.getText().toLowerCase().trim(),
                        new String(senha.getPassword()).toLowerCase().trim(),
                        new String(confSenha.getPassword()).toLowerCase().trim(),
                        this
        ));
    }
}