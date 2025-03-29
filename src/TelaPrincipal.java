import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaPrincipal extends JFrame {
    private Conta conta;
    private JLabel saldoLabel;

    public TelaPrincipal(Conta conta) {
        this.conta = conta;
        setTitle("Gestão Financeira Pessoal");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titulo = new JLabel("Gestão Financeira Pessoal", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(titulo, BorderLayout.NORTH);

        // Painel do saldo
        JPanel saldoPanel = new JPanel(new BorderLayout());
        saldoPanel.setBorder(BorderFactory.createTitledBorder("Saldo Atual"));
        saldoLabel = new JLabel("R$ 0,00", SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        atualizarSaldo();
        saldoPanel.add(saldoLabel, BorderLayout.CENTER);
        panel.add(saldoPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel botoesPanel = new JPanel(new GridLayout(1, 4, 10, 0));

        // Botão Adicionar Transação
        JButton btnAdicionar = criarBotao("Adicionar Transação", Color.GREEN.darker());
        btnAdicionar.addActionListener(e -> {
            new AdicionarTransacao(conta).setVisible(true);
            dispose();
        });

        // Botão Consultar Histórico
        JButton btnConsultar = criarBotao("Consultar Histórico", Color.BLUE);
        btnConsultar.addActionListener(e -> new ConsultaHistorico(conta).setVisible(true));

        // Botão Gerenciar Categorias
        JButton btnCategorias = criarBotao("Gerenciar Categorias", Color.ORANGE.darker());
        btnCategorias.addActionListener(e -> new GerenciarCategorias(conta).setVisible(true));

        // Botão Sair
        JButton btnSair = criarBotao("Sair", Color.RED.darker());
        btnSair.addActionListener(e -> {
            new Login(conta).setVisible(true);
            dispose();
        });

        botoesPanel.add(btnAdicionar);
        botoesPanel.add(btnConsultar);
        botoesPanel.add(btnCategorias);
        botoesPanel.add(btnSair);

        panel.add(botoesPanel, BorderLayout.SOUTH);

        // Atualiza o saldo quando a janela ganha foco
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                atualizarSaldo();
            }
        });

        add(panel);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        return botao;
    }

    private void atualizarSaldo() {
        float saldo = conta.getSaldo();
        saldoLabel.setText(String.format("R$ %.2f", saldo));
        saldoLabel.setForeground(saldo >= 0 ? Color.BLUE : Color.RED);
    }
}