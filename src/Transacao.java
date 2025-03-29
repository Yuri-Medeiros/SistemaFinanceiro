import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transacao {
    private final float valor;
    private final String categoria;
    private final String descricao;
    private final LocalDate data;
    private final char tipo; // 'E' para Entrada (receita), 'S' para Saída (despesa)

    public Transacao(float valor, String categoria, String descricao, LocalDate data, char tipo) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria não pode ser vazia");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        if (tipo != 'E' && tipo != 'S') {
            throw new IllegalArgumentException("Tipo deve ser 'E' (Entrada) ou 'S' (Saída)");
        }

        this.valor = valor;
        this.categoria = categoria.trim();
        this.descricao = descricao.trim();
        this.data = data;
        this.tipo = tipo;
    }

    // Getters
    public float getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public char getTipo() {
        return tipo;
    }

    // Método para exibição formatada
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String tipoStr = (tipo == 'E') ? "Receita" : "Despesa";
        return String.format("%s | %-7s | %-15s | %-30s | R$ %10.2f",
                data.format(formatter),
                tipoStr,
                categoria,
                descricao,
                valor);
    }

    // Método para exibição no console (opcional)
    public void exibirTransacao() {
        System.out.println(this.toString());
    }

    // Métodos equals e hashCode para comparação de transações
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Float.compare(transacao.valor, valor) == 0 &&
                tipo == transacao.tipo &&
                Objects.equals(categoria, transacao.categoria) &&
                Objects.equals(descricao, transacao.descricao) &&
                Objects.equals(data, transacao.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, categoria, descricao, data, tipo);
    }

    // Método para obter valor com sinal (positivo para receitas, negativo para despesas)
    public float getValorComSinal() {
        return (tipo == 'E') ? valor : -valor;
    }
}