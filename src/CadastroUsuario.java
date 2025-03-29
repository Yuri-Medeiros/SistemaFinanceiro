import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroUsuario extends JFrame {
    private final JTextField txtUsuario;
    private final JPasswordField txtSenha;
    private final JPasswordField txtConfirmarSenha;

    public CadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titulo = new JLabel("Cadastro de Usuário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titulo, BorderLayout.NORTH);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 10));

        // Componentes
        JLabel lblUsuario = new JLabel("Usuário:");
        txtUsuario = new JTextField();

        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField();

        JLabel lblConfirmar = new JLabel("Confirmar Senha:");
        txtConfirmarSenha = new JPasswordField();

        // Adicionando componentes ao formulário
        formPanel.add(lblUsuario);
        formPanel.add(txtUsuario);
        formPanel.add(lblSenha);
        formPanel.add(txtSenha);
        formPanel.add(lblConfirmar);
        formPanel.add(txtConfirmarSenha);

        panel.add(formPanel, BorderLayout.CENTER);

        // Botão de cadastro
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(new Color(0, 120, 215));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.addActionListener(this::realizarCadastro);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnCadastrar);
        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void realizarCadastro(ActionEvent e) {
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmacao = new String(txtConfirmarSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!senha.equals(confirmacao)) {
            JOptionPane.showMessageDialog(this,
                    "As senhas não coincidem!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aqui você adicionaria a lógica para salvar o usuário
        JOptionPane.showMessageDialog(this,
                "Usuário cadastrado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}