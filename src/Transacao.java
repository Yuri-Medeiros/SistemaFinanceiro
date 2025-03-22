import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transacao {

    private final Character tipo;
    private final float valor;
    private final String categoria;
    private final String descricao;
    private final LocalDate data;

    public Transacao(float valor, String categoria, String descricao, LocalDate data, Character tipo) {
        this.tipo = tipo;
        this.valor = valor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.data = data;
    }

    public Character getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getData() {
        return data;
    }

    public Float getValor() {
        return valor;
    }

    public void exibirTransacao(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataFormatada = dtf.format(this.data);
        System.out.println(dataFormatada + " - " + this.categoria + " - " + this.descricao + " - " + this.valor);
    }
}
