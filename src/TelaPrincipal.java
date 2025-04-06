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

        btnAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame telaLancamento = new JFrame();

                telaLancamento.setTitle("Gestão Financeira - Adicionar Transação");
                telaLancamento.setSize(400, 300);
                telaLancamento.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                telaLancamento.setLocationRelativeTo(null);

                telaLancamento.setLayout(new BorderLayout());

                // título
                JLabel titulo = new JLabel("Adicionar Nova Transação", SwingConstants.CENTER);
                titulo.setFont(new Font("Arial", Font.BOLD, 18));
                telaLancamento.add(titulo, BorderLayout.NORTH);

                // formulário
                JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                panel.add(new JLabel("Valor:\nApenas números e separe por ."));
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
                    JOptionPane.showMessageDialog(null, "Erro ao aplicar máscara na data.");
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
                            JOptionPane.showMessageDialog(TelaPrincipal.this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if(campoValor.getText().matches("\\d+")){

                            // Tenta converter para LocalDate
                            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                            campoValor.setText(campoValor.getText().replace(',', '.'));
                            float valor = Float.parseFloat(campoValor.getText());

                            try {
                                LocalDate dataConvertida = LocalDate.parse(campoData.getText(), formato);
                                JOptionPane.showMessageDialog(null, "Data válida: " + dataConvertida);

                                if(campoCategoria.getSelectedItem().equals("Receita")){
                                    //CONTINUAR AQUI
                                    //Main.contaAtiva.depositar(valor, );
                                }
                            } catch (DateTimeParseException ex) {
                                JOptionPane.showMessageDialog(null, "Data inválida!");
                            }
                        }
                    }
                });

                btnCancelar.addActionListener(ev -> dispose());

                botoesPanel.add(btnSalvar);
                botoesPanel.add(btnCancelar);

                telaLancamento.add(botoesPanel, BorderLayout.SOUTH);

                telaLancamento.setVisible(true);
            }
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
}
