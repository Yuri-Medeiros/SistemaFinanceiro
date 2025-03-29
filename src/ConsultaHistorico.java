import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConsultaHistorico extends JFrame {
    private Conta conta;

    public ConsultaHistorico(Conta conta) {
        this.conta = conta;
        setTitle("Consulta de Histórico");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel filtrosPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Filtros
        JLabel dataInicioLabel = new JLabel("Data Início (DD/MM/AAAA):");
        JTextField dataInicioField = new JTextField();
        JLabel dataFimLabel = new JLabel("Data Fim (DD/MM/AAAA):");
        JTextField dataFimField = new JTextField();

        JLabel tipoLabel = new JLabel("Tipo:");
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Todos", "Receita", "Despesa"});

        filtrosPanel.add(dataInicioLabel);
        filtrosPanel.add(dataInicioField);
        filtrosPanel.add(dataFimLabel);
        filtrosPanel.add(dataFimField);
        filtrosPanel.add(tipoLabel);
        filtrosPanel.add(tipoCombo);

        // Área de texto para resultados
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // Botão de consulta
        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(e -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inicio = dataInicioField.getText().isEmpty() ? LocalDate.MIN : LocalDate.parse(dataInicioField.getText(), formatter);
                LocalDate fim = dataFimField.getText().isEmpty() ? LocalDate.MAX : LocalDate.parse(dataFimField.getText(), formatter);

                String tipoSelecionado = (String) tipoCombo.getSelectedItem();
                List<Character> tipos = new ArrayList<>();
                if (tipoSelecionado.equals("Todos") || tipoSelecionado.equals("Receita")) {
                    tipos.add('E');
                }
                if (tipoSelecionado.equals("Todos") || tipoSelecionado.equals("Despesa")) {
                    tipos.add('S');
                }

                resultadoArea.setText("");
                for (Transacao transacao : conta.getExtrato()) {
                    boolean validaData = (transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim))
                            || transacao.getData().equals(inicio)
                            || transacao.getData().equals(fim);
                    boolean validaTipo = tipos.contains(transacao.getTipo());

                    if (validaData && validaTipo) {
                        resultadoArea.append(transacao.toString() + "\n");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(filtrosPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(consultarButton, BorderLayout.SOUTH);

        add(panel);
    }
}