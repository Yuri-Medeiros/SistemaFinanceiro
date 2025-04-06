import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Conta extends JFrame{

    private float saldo;
    private final List<Transacao> extrato;
    private final List<String> categorias;
    private final String login;
    private final String senha;

    public Conta(String login, String senha) {
        this.saldo = 0;
        this.extrato = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.login = login;
        this.senha = senha;
    }

    public String getLogin(){
        return this.login;
    }

    public String getSenha(){return this.senha;}

    public float getSaldo(){
        return saldo;
    }

    public void depositar(float valor, LocalDate data, String categoria, String descricao){
        Transacao deposito = new Transacao(valor, categoria, descricao, data, 'E');
        this.extrato.add(deposito);
        this.saldo += valor;
    }

    public void sacar(float valor, LocalDate data, String categoria, String descricao){
        Transacao saque = new Transacao(valor, categoria, descricao, data, 'S');
        this.extrato.add(saque);
        this.saldo -= valor;
    }

    public void adicionarCategoria(String categoria){
        this.categorias.add(categoria);
    }

    public void editarCategoria(String categoria){

        exibirCategorias();

        System.out.print("Informe o número da categoria a ser editada:");

        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        System.out.print("Informe a nova descrição da categoria:");
        String novaCategoria = scanner.nextLine();

        this.categorias.remove(opcao - 1);
        this.categorias.add(novaCategoria);
        scanner.close();

        System.out.println("Categoria editada com sucesso!");
    }

    public void removerCategoria(){

        exibirCategorias();

        System.out.print("Informe o número da categoria a ser excluida:");

        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        this.categorias.remove(opcao - 1);

        System.out.println("Categoria removida com sucesso!");
        scanner.close();
    }

    public void exibirCategorias(){

        System.out.println("Listando categorias:");

        for (int i = 0; i < this.categorias.size(); i++){
            System.out.println(i + ") " + this.categorias.get(i));
        }
    }

    public void mostrarExtrato(List<String> categoria, LocalDate inicio, LocalDate fim, List<Character> tipo){

        for(Transacao transacao : this.extrato){

            boolean validaCategoria = categoria.contains(transacao.getCategoria());
            boolean validaData = ((transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim))
                    || transacao.getData().equals(inicio)
                    || transacao.getData().equals(fim));
            boolean validaTipo = tipo.contains(transacao.getTipo());

            if(validaCategoria && validaTipo && validaData){
                transacao.exibirTransacao();
                System.out.println("---------------------------------------------------");
            }
        }
    }

    public Map<Character, Float> mostrarSaldoPorCategoria(LocalDate inicio, LocalDate fim){

        Map<Character, Float> valorTotal = new HashMap<>();
        valorTotal.put('E', 0f);
        valorTotal.put('S', 0f);

        for(Transacao transacao : this.extrato){

            boolean validaData = ((transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim))
                    || transacao.getData().equals(inicio)
                    || transacao.getData().equals(fim));

            if(validaData){

                Character tipo = transacao.getTipo();
                float valor = transacao.getValor() + valorTotal.get(tipo);

                valorTotal.put(tipo, valor);
            }
        }

        return valorTotal;
    }
}
