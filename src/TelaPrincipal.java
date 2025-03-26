import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Gestão Financeira Pessoal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Criando o painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Criando o título
        JLabel titulo = new JLabel("Gestão Financeira Pessoal", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        // Exibição do saldo
        JLabel saldoLabel = new JLabel("Saldo Atual: R$ 0,00", SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(saldoLabel, BorderLayout.CENTER);

        // Criando botões
        JPanel panelBotao = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Transação");
        JButton btnCategorias = new JButton("Consultar");
        JButton btnSair = new JButton("Logout");

        btnAdicionar.addActionListener(e -> new AdicionarTransacao());
        btnSair.addActionListener(e -> new Login());

        panelBotao.add(btnAdicionar);
        panelBotao.add(btnCategorias);
        panelBotao.add(btnSair);
        panel.add(panelBotao, BorderLayout.SOUTH);

        // Adicionando o painel principal à janela
        add(panel);

        // Tornar a janela visível
        setVisible(true);
    }

        Conta conta = new Conta();                  //Cria conta
        /*
        *  APLICAR LÓGICA AQUI --------
        *
        *  Para realizar as operações usem a classe criada acima: conta
        *  Abra a classe Conta para verificar os métodos que ela possui
        *
        *  Criei uma outa classe Transacao para as transações
        *
        *  Todos os métodos para os requisitos funcionais estão criados, basta usar e exibir na tela
        *
        *
        * */
}
