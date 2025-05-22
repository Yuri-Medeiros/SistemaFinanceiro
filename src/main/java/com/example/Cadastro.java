package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Cadastro extends JFrame {

    /*
    Define o campo para preencher oo usuario,
    campo para preencher a senha
    e botão para ação
    */
    private final JTextField usuario;
    private final JPasswordField senha;
    private final JPasswordField confSenha;
    private final JButton botao;

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

        usuario = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        add(usuario, gbc);

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

        botao = new JButton("Cadastrar");
        botao.setBackground(new Color(0, 120, 215));
        botao.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botao, gbc);

        botao.addActionListener(new ActionListener() {

            //Armazena o Cadastro
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usuario.getText();
                String password = new String(senha.getPassword());
                String confPassword = new String(confSenha.getPassword());

                if(username.isEmpty() || password.isEmpty() || confPassword.isEmpty()){
                    JOptionPane.showMessageDialog(Cadastro.this, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(!password.equals(confPassword)){
                    JOptionPane.showMessageDialog(Cadastro.this, "Senhas não conferem", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Verifica se o Login ja existe
                if(!checkLogin()){

                    //Adiciona conta a lista de contas e fecha a tela de Cadastro
                    Main.contasCadastradas.add(new Conta(username, confPassword));
                    dispose();
                } else {
                    //Exibe erro caso Login ja exista
                    JOptionPane.showMessageDialog(Cadastro.this, "Usuário ja existe! Escolha outro", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private boolean checkLogin(){

        for(Conta conta : Main.contasCadastradas){
            if(conta.getLogin().equals(usuario.getText())) {
                return true;
            }
        }
        return false;
    }
}