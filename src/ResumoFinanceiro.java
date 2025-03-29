import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ResumoFinanceiro extends JFrame {
    private final Conta conta;

    public ResumoFinanceiro(Conta conta) {
        this.conta = conta;
        initUI();
    }

    private void initUI() {
        setTitle("Resumo Financeiro");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titulo = new JLabel("Resumo Financeiro", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        // Componentes
        JTextField txtInicio = new JTextField(LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JTextField txtFim = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JTextArea txtResultado = new JTextArea();

        // Configurações
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Layout
        JPanel filtrosPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        filtrosPanel.add(new JLabel("Data Início (DD/MM/AAAA):"));
        filtrosPanel.add(txtInicio);
        filtrosPanel.add(new JLabel("Data Fim (DD/MM/AAAA):"));
        filtrosPanel.add(txtFim);

        JButton btnAtualizar = new JButton("Atualizar Resumo");
        btnAtualizar.addActionListener(e -> atualizarResumo(
                txtInicio.getText(),
                txtFim.getText(),
                txtResultado
        ));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(filtrosPanel, BorderLayout.NORTH);
        topPanel.add(btnAtualizar, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);
        add(panel);

        // Atualização inicial
        btnAtualizar.doClick();
    }

    private void atualizarResumo(String dataInicioStr, String dataFimStr, JTextArea txtResultado) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inicio = LocalDate.parse(dataInicioStr, formatter);
            LocalDate fim = LocalDate.parse(dataFimStr, formatter);

            if (inicio.isAfter(fim)) {
                throw new IllegalArgumentException("Data início deve ser anterior à data fim");
            }

            // Método alternativo caso mostrarSaldoPorCategoria não exista
            float receitas = calcularTotalPorTipo(inicio, fim, 'E');
            float despesas = calcularTotalPorTipo(inicio, fim, 'S');
            float saldo = receitas - despesas;

            Map<String, Float> porCategoria = conta.getResumoPorCategoria(inicio, fim);

            // Construindo o resultado
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Período: %s a %s%n%n",
                    inicio.format(formatter),
                    fim.format(formatter)));

            sb.append("=== TOTAIS ===\n");
            sb.append(String.format("Receitas: R$ %,.2f%n", receitas));
            sb.append(String.format("Despesas: R$ %,.2f%n", despesas));
            sb.append(String.format("Saldo:    R$ %,.2f%n%n", saldo));

            sb.append("=== POR CATEGORIA ===\n");
            porCategoria.forEach((categoria, valor) ->
                    sb.append(String.format("%-20s R$ %10.2f%n", categoria, valor))
            );

            txtResultado.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método auxiliar caso mostrarSaldoPorCategoria não exista
    private float calcularTotalPorTipo(LocalDate inicio, LocalDate fim, char tipo) {
        return conta.getExtrato().stream()
                .filter(t -> t.getTipo() == tipo)
                .filter(t -> !t.getData().isBefore(inicio) && !t.getData().isAfter(fim))
                .map(Transacao::getValor)
                .reduce(0.0f, Float::sum);
    }
}