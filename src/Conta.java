import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conta {
    private float saldo;
    private final List<Transacao> extrato;
    private final List<String> categorias;

    public Conta() {
        this.saldo = 0;
        this.extrato = new ArrayList<>();
        this.categorias = new ArrayList<>();

        // Categorias padrão
        this.categorias.add("Alimentação");
        this.categorias.add("Transporte");
        this.categorias.add("Salário");
        this.categorias.add("Lazer");
        this.categorias.add("Moradia");
    }

    // Métodos para transações
    public void depositar(float valor, LocalDate data, String categoria, String descricao) {
        validarTransacao(valor, categoria, descricao, data);
        Transacao deposito = new Transacao(valor, categoria, descricao, data, 'E');
        this.extrato.add(deposito);
        this.saldo += valor;
    }

    public void sacar(float valor, LocalDate data, String categoria, String descricao) {
        validarTransacao(valor, categoria, descricao, data);
        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        Transacao saque = new Transacao(valor, categoria, descricao, data, 'S');
        this.extrato.add(saque);
        this.saldo -= valor;
    }

    private void validarTransacao(float valor, String categoria, String descricao, LocalDate data) {
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
    }

    // Métodos para categorias
    public void adicionarCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria não pode ser vazia");
        }
        if (this.categorias.contains(categoria)) {
            throw new IllegalArgumentException("Categoria já existe");
        }
        this.categorias.add(categoria.trim());
    }

    public void editarCategoria(int indice, String novaCategoria) {
        if (indice < 0 || indice >= categorias.size()) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        this.categorias.set(indice, novaCategoria.trim());
    }

    public void removerCategoria(int indice) {
        if (indice < 0 || indice >= categorias.size()) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        this.categorias.remove(indice);
    }

    // Métodos de consulta
    public Map<String, Float> getResumoPorCategoria(LocalDate inicio, LocalDate fim) {
        Map<String, Float> resumo = new HashMap<>();

        for (Transacao t : extrato) {
            if (!t.getData().isBefore(inicio) && !t.getData().isAfter(fim)) {
                float valor = t.getTipo() == 'E' ? t.getValor() : -t.getValor();
                resumo.merge(t.getCategoria(), valor, Float::sum);
            }
        }
        return resumo;
    }

    public Map<Character, Float> mostrarSaldoPorCategoria(LocalDate inicio, LocalDate fim) {
        Map<Character, Float> totais = new HashMap<>();
        totais.put('E', 0.0f); // Receitas
        totais.put('S', 0.0f); // Despesas

        for (Transacao t : extrato) {
            if (!t.getData().isBefore(inicio) && !t.getData().isAfter(fim)) {
                totais.put(t.getTipo(), totais.get(t.getTipo()) + t.getValor());
            }
        }
        return totais;
    }

    // Getters
    public float getSaldo() {
        return saldo;
    }

    public List<Transacao> getExtrato() {
        return new ArrayList<>(extrato);
    }

    public List<String> getCategorias() {
        return new ArrayList<>(categorias);
    }


    public void exibirCategorias() {
        System.out.println("\n--- CATEGORIAS ---");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println(i + " - " + categorias.get(i));
        }
    }
}