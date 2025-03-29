import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdicionarTransacao extends JFrame {
    // Declaração dos componentes
    private JTextField campoValor;
    private JComboBox<String> campoCategoria;
    private JTextField campoData;
    private JTextField campoDescricao;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private final Conta conta;

    public AdicionarTransacao(Conta conta) {
        this.conta = conta;
        setTitle("Gestão Financeira - Adicionar Transação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Adicionar Nova Transação", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Painel do formulário
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Componentes do formulário
        panel.add(new JLabel("Valor:"));
        campoValor = new JTextField();
        panel.add(campoValor);

        panel.add(new JLabel("Categoria:"));
        campoCategoria = new JComboBox<>(new String[]{"Receita", "Despesa"});
        panel.add(campoCategoria);

        panel.add(new JLabel("Data (DD/MM/AAAA):"));
        campoData = new JTextField();
        panel.add(campoData);

        panel.add(new JLabel("Descrição:"));
        campoDescricao = new JTextField();
        panel.add(campoDescricao);

        add(panel, BorderLayout.CENTER);

        // Painel de botões
        JPanel botoesPanel = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        // Ação do botão Salvar
        btnSalvar.addActionListener(e -> {
            try {
                float valor = Float.parseFloat(campoValor.getText());
                String categoria = (String) campoCategoria.getSelectedItem();
                String dataStr = campoData.getText();
                String descricao = campoDescricao.getText();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(dataStr, formatter);

                if (categoria.equals("Receita")) {
                    conta.depositar(valor, data, categoria, descricao);
                } else {
                    conta.sacar(valor, data, categoria, descricao);
                }

                JOptionPane.showMessageDialog(this,
                        "Transação adicionada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Valor inválido! Use apenas números.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao adicionar transação: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação do botão Cancelar
        btnCancelar.addActionListener(e -> dispose());

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnCancelar);
        add(botoesPanel, BorderLayout.SOUTH);
    }
}