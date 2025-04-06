import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class TelaPrincipal extends JFrame {

    JFormattedTextField campoData = null;

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

        String saldo = "Saldo atual: R$" + Main.contaAtiva.getSaldo();

        // Exibição do saldo
        JLabel saldoLabel = new JLabel(saldo, SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(saldoLabel, BorderLayout.CENTER);

        // Criando botões
        JPanel panelBotao = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Transação");
        JButton btnCategorias = new JButton("Consultar");
        JButton btnSair = new JButton("Logout");

        panelBotao.add(btnAdicionar);
        panelBotao.add(btnCategorias);
        panelBotao.add(btnSair);
        panel.add(panelBotao, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {

            dispose();
            novaTransacao();
        });

        btnCategorias.addActionListener(e -> {

            dispose();
            consulta();
        });

        //btnAdicionar.addActionListener();
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.contaAtiva = null;
                dispose();
                SwingUtilities.invokeLater(() -> new Login().setVisible(true));
            }
        });

        // Adicionando o painel principal à janela
        add(panel);
    }

    private void novaTransacao(){

        JFrame telaLancamento = new JFrame();
        telaLancamento.setVisible(true);

        telaLancamento.setTitle("Gestão Financeira - Adicionar Transação");
        telaLancamento.setSize(400, 300);
        telaLancamento.setLocationRelativeTo(null);

        telaLancamento.setLayout(new BorderLayout());

        // título
        JLabel titulo = new JLabel("Adicionar Nova Transação", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        telaLancamento.add(titulo, BorderLayout.NORTH);

        // formulário
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Valor: (Apenas números e separe por .)"));
        JTextField campoValor = new JTextField();
        panel.add(campoValor);

        panel.add(new JLabel("Categoria:"));
        JComboBox<String> campoCategoria = new JComboBox<>(new String[]{"Escolha uma opção...","Receita", "Despesa"});
        panel.add(campoCategoria);

        panel.add(new JLabel("Data:"));

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');

            campoData = new JFormattedTextField(mascaraData); // 2. Instanciar dentro do try
            campoData.setColumns(10);

            panel.add(campoData);
        } catch (ParseException er) {
            JOptionPane.showMessageDialog(null, "Erro ao armazenar a data. Tente novamente!");
            return;
        }

        panel.add(new JLabel("Descrição:"));
        JTextField campoDescricao = new JTextField();
        panel.add(campoDescricao);

        telaLancamento.add(panel, BorderLayout.CENTER);

        // botões
        JPanel botoesPanel = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(campoValor.getText().isEmpty() ||
                        campoCategoria.getSelectedIndex() == 0 ||
                        campoDescricao.getText().isEmpty() ||
                        campoData.getText().contains("_")){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(campoValor.getText().matches("\\d+")){

                    // Tenta converter para LocalDate
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    campoValor.setText(campoValor.getText().replace(',', '.'));
                    float valor = Float.parseFloat(campoValor.getText());

                    String selecionado = (String) campoCategoria.getSelectedItem();

                    try {
                        LocalDate dataConvertida = LocalDate.parse(campoData.getText(), formato);

                        if(campoCategoria.getSelectedItem().equals("Receita")){
                            Main.contaAtiva.depositar(valor, dataConvertida, selecionado, campoDescricao.getText());
                        } else {
                            Main.contaAtiva.sacar(valor, dataConvertida, selecionado, campoDescricao.getText());
                        }

                        telaLancamento.dispose();
                        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Informe apenas números para Valor!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(ev -> {

            telaLancamento.dispose();
            SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
        });

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnCancelar);

        telaLancamento.add(botoesPanel, BorderLayout.SOUTH);

        telaLancamento.setVisible(true);
    }

    private void consulta(){


    }
}
