package com.example.util;

import javax.swing.*;
import java.awt.*;

public class CriarBotao {

    public static JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);

        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        return botao;
    }
}
