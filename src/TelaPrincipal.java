import javax.swing.*;
import java.util.Objects;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(){

        //Define propriedades da pagina
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icone.png"));
        setIconImage(icon.getImage());
        setTitle("Controle Financeiro");
        setSize(600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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
}
