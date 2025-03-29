import javax.swing.SwingUtilities;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Cria uma instância de Conta (nosso "banco de dados" em memória)
        Conta conta = new Conta();

        // Adiciona alguns dados iniciais para demonstração
        inicializarDadosDemonstracao(conta);

        // Inicia a interface gráfica na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            Login login = new Login(conta);
            login.setVisible(true);
        });
    }

    private static void inicializarDadosDemonstracao(Conta conta) {
        try {
            LocalDate hoje = LocalDate.now();

            // Adiciona algumas transações de exemplo
            conta.depositar(2500.00f, hoje.minusDays(5), "Salário", "Pagamento mensal");
            conta.sacar(350.50f, hoje.minusDays(3), "Alimentação", "Supermercado");
            conta.sacar(120.00f, hoje.minusDays(2), "Transporte", "Combustível");
            conta.depositar(150.00f, hoje.minusDays(1), "Freelance", "Trabalho extra");
            conta.sacar(80.00f, hoje, "Lazer", "Cinema");

            // Adiciona algumas categorias extras
            conta.adicionarCategoria("Educação");
            conta.adicionarCategoria("Saúde");

        } catch (Exception e) {
            System.err.println("Erro ao carregar dados de demonstração: " + e.getMessage());
        }
    }
}