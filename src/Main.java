import javax.swing.SwingUtilities;

public class Main {

    //Ponto de partida de execução
    public static void main(String[] args){

        //Executa a interface gráfica chamando criando a classe Login
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}

