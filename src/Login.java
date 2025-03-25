import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Login extends JFrame {

    /*
    Define o campo para preencher oo usuario,
    campo para preencher a senha
    e botão para ação
    */
    private JTextField usuario;
    private JPasswordField senha;
    private JButton botao;

    public Login() {
        setTitle("Login");                                          //Define titulo da pagina
        setSize(300, 200);                             //Define tamanho da tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            //Define que ao fechar, encerra o programa
        setLocationRelativeTo(null);                              //Define inicialização no centro do monitor
        setLayout(new GridBagLayout());                           //Cria uma grade para ajudar na alocação dos elementos, cresce conforme adicionamos

        GridBagConstraints gbc = new GridBagConstraints();         //Cria a classe que organiza os elementos
        gbc.insets = new Insets(5, 5, 5, 5);    //Define espaçamento de 5px entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;                    //Define orientação horizontal para elementos

        JLabel userLabel = new JLabel("Usuário:");              //Cria texto para usuario
        gbc.gridx = 0;                                              //Define posições no grid
        gbc.gridy = 0;
        add(userLabel, gbc);                                        //Insere o elemento, repete o mesmo para os demais

        usuario = new JTextField(15);
        gbc.gridx = 1;
        add(usuario, gbc);

        JLabel passLabel = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        senha = new JPasswordField(15);
        gbc.gridx = 1;
        add(senha, gbc);

        botao = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;                                          //Define largura do elemento
        add(botao, gbc);

        botao.addActionListener(new ActionListener() {              //Define ação para acionamento do botão

            @Override
            public void actionPerformed(ActionEvent e) {            //Função executada pós aperto do botão
                String username = usuario.getText();                //Armazena usuario e senha como String
                String password = new String(senha.getPassword());

                if (username.equals("Fulano") && password.equals("1234")) { //Verifica informações
                    dispose();                                              //Fecha tela de Login
                    new TelaPrincipal().setVisible(true);                   //Da start na tela principal
                } else {    //Se false, exibe mensagem de erro
                    JOptionPane.showMessageDialog(Login.this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}