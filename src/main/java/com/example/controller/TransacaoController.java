package com.example.controller;

import com.example.view.NovaTransacao;

import javax.swing.*;

public class TransacaoController {

    public void abrirTransacao() {

        SwingUtilities.invokeLater(() -> new NovaTransacao().setVisible(true));
    }
}
