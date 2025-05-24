package com.example.view;

import com.example.controller.ContaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    /*
    Define o campo para preencher oo usuario,
    campo para preencher a senha
    e botão para ação
    */
    private final JTextField login;
    private final JPasswordField senha;
    private final ContaController c = new ContaController();

    public Login() {
        setTitle("Login");                                          //Define titulo da pagina
        setSize(300, 200);                             //Define tamanho da tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            //Define que ao fechar, encerra o programa
        setLocationRelativeTo(null);                              //Define inicialização no centro do monitor
        setLayout(new GridBagLayout());                           //Cria uma grade para ajudar na alocação dos elementos, cresce conforme adicionamos

        GridBagConstraints gbc = new GridBagConstraints();         //Cria a classe que organiza os elementos
        gbc.insets = new Insets(5, 5, 5, 5);    //Define espaçamento de 5px entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;                    //Define orientação horizontal para elementos

        //Insere titulo do campo de usuario
        JLabel userLabel = new JLabel("Login:");                //Cria classe de descricao
        gbc.gridx = 0;                                              //Define posição em X do elemento
        gbc.gridy = 0;                                             //Define posição em Y do elemento
        gbc.gridwidth = 1;                                        //Define posição fixa do elemento
        gbc.anchor = GridBagConstraints.EAST;                    //Define a posição absoluta do elemento
        add(userLabel, gbc);                                    //Insere o elemento, repete o mesmo para os demais

        //Insere campo de usuario
        login = new JTextField(15);
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
        senha = new JPasswordField(15);
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

        //Define ação para acionamento do botão
        botaoLogin.addActionListener(c.logar(
                login.getText().toLowerCase().trim(),
                new String(senha.getPassword()).toLowerCase().trim(),
                this
        ));

        //Ação para botão de cadastro
        botaoCadastro.addActionListener(new ActionListener() {

            //Inicia tela de Cadastro
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new Cadastro().setVisible(true));
            }
        });
    }
}