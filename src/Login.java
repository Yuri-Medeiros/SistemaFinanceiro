import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Login extends JFrame {
    private JTextField usuario;
    private JPasswordField senha;
    private JButton botaoLogin, botaoCadastro;
    private Conta conta;

    public Login(Conta conta) {
        this.conta = conta;
        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label e campo de usuário
        JLabel userLabel = new JLabel("Usuário:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        usuario = new JTextField(15);
        gbc.gridx = 1;
        add(usuario, gbc);

        // Label e campo de senha
        JLabel passLabel = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        senha = new JPasswordField(15);
        gbc.gridx = 1;
        add(senha, gbc);

        // Botão de Login
        botaoLogin = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(botaoLogin, gbc);

        // Botão de Cadastro (novo)
        botaoCadastro = new JButton("Cadastrar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(botaoCadastro, gbc);

        // Ação do botão Login
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usuario.getText();
                String password = new String(senha.getPassword());

                if (username.equals("admin") && password.equals("1234")) { // Credenciais padrão
                    dispose();
                    new TelaPrincipal(conta).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "Usuário ou senha incorretos!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão Cadastro (novo)
        botaoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroUsuario().setVisible(true);
            }
        });
    }
}