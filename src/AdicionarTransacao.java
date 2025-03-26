import javax.swing.*;
import java.awt.*;

public class AdicionarTransacao extends JFrame {

    public AdicionarTransacao() {
        setTitle("Gestão Financeira - Adicionar Transação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // título
        JLabel titulo = new JLabel("Adicionar Nova Transação", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // formulário
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Valor: Apenas Números"));
        JTextField campoValor = new JTextField();
        panel.add(campoValor);

        panel.add(new JLabel("Categoria:"));
        JComboBox<String> campoCategoria = new JComboBox<>(new String[]{"Receita", "Despesa"});
        panel.add(campoCategoria);

        panel.add(new JLabel("Data:"));
        JTextField campoData = new JTextField("DD/MM/AAAA");
        panel.add(campoData);

        panel.add(new JLabel("Descrição:"));
        JTextField campoDescricao = new JTextField();
        panel.add(campoDescricao);

        add(panel, BorderLayout.CENTER);

        // botões
        JPanel botoesPanel = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> new TelaPrincipal());
        btnSalvar.addActionListener(e -> new TelaPrincipal());

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnCancelar);

        add(botoesPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}


