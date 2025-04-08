import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class TelaPrincipal extends JFrame {

    JFormattedTextField campoData = null;
    JFormattedTextField campoDataFiltroInicial = null;
    JFormattedTextField campoDataFiltroFinal = null;

    public TelaPrincipal() {
        setTitle("Gestão Financeira Pessoal");
        setSize(500, 400); // Altura ajustada
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Gestão Financeira Pessoal", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        // painel Centro
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));

        // Saldo Atual
        String saldoTexto = String.format("Saldo atual: R$%.2f", Main.contaAtiva.getSaldo());
        JLabel saldoLabel = new JLabel(saldoTexto, SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        saldoLabel.setOpaque(true);
        saldoLabel.setBackground(new Color(230, 255, 230)); // verde claro
        saldoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saldoLabel.setMaximumSize(new Dimension(400, 40));
        saldoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        painelCentro.add(Box.createVerticalStrut(30)); // espaço acima do saldo
        painelCentro.add(saldoLabel);
        painelCentro.add(Box.createVerticalStrut(15)); // espaço abaixo do saldo

        // Calculo Receitas-Despesas
        float totalReceitas = 0f;
        float totalDespesas = 0f;

        for (Transacao t : Main.contaAtiva.getExtrato()) {
            if (t.getTipo() == 'E') {
                totalReceitas += t.getValor();
            } else if (t.getTipo() == 'S') {
                totalDespesas += t.getValor();
            }
        }

        // Painel Lucros-Gastos
        JPanel painelLucrosGastos = new JPanel(new GridLayout(1, 2, 10, 0));
        JLabel labelLucros = new JLabel(String.format("Lucros: R$%.2f", totalReceitas), SwingConstants.CENTER);
        JLabel labelGastos = new JLabel(String.format("Gastos: R$%.2f", totalDespesas), SwingConstants.CENTER);

        labelLucros.setFont(new Font("Arial", Font.PLAIN, 16));
        labelLucros.setForeground(new Color(0, 130, 0)); // verde escuro
        labelGastos.setFont(new Font("Arial", Font.PLAIN, 16));
        labelGastos.setForeground(new Color(200, 0, 0)); // vermelho escuro

        painelLucrosGastos.setMaximumSize(new Dimension(400, 30));
        painelCentro.add(painelLucrosGastos);
        painelLucrosGastos.add(labelLucros);
        painelLucrosGastos.add(labelGastos);

        panel.add(painelCentro, BorderLayout.CENTER);

        //Botões
        JPanel panelBotao = new JPanel(new GridLayout(1, 4, 10, 0));
        JButton btnAdicionar = criarBotao("Adicionar Transação", new Color(0, 168, 107));
        JButton btnConsulta = criarBotao("Consultar", new Color(0, 120, 215));
        JButton btnCategorias = criarBotao("Categorias", new Color(255, 140, 0));
        JButton btnSair = criarBotao("Logout", new Color(215, 60, 60));

        panelBotao.add(btnAdicionar);
        panelBotao.add(btnConsulta);
        panelBotao.add(btnCategorias);
        panelBotao.add(btnSair);
        panel.add(panelBotao, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            novaTransacao();
            dispose();
        });

        btnConsulta.addActionListener(e -> {
            consulta();
            dispose();
        });

        btnCategorias.addActionListener(e -> {
            categorias();
        });

        btnSair.addActionListener(e -> {
            Main.contaAtiva = null;
            dispose();
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        });

        add(panel);
    }



    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);

        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        return botao;
    }

    private void novaTransacao(){

        JFrame telaLancamento = new JFrame();
        telaLancamento.setVisible(true);

        telaLancamento.setTitle("Gestão Financeira - Adicionar Transação");
        telaLancamento.setSize(400, 300);
        telaLancamento.setLocationRelativeTo(null);

        telaLancamento.setLayout(new BorderLayout());

        // título
        JLabel titulo = new JLabel("Adicionar Nova Transação", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        telaLancamento.add(titulo, BorderLayout.NORTH);

        // formulário
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Valor: (Apenas números e separe por .)"));
        JTextField campoValor = new JTextField();
        panel.add(campoValor);

        panel.add(new JLabel("tipo:"));
        JComboBox<String> campoTipo = new JComboBox<String>(new String[]{"", "Receita", "Despesa"});
        panel.add(campoTipo);

        panel.add(new JLabel("Categoria:"));
        JComboBox<String> campoCategoria = new JComboBox<String>(Main.contaAtiva.getCategorias().toArray(new String[Main.contaAtiva.getCategorias().size()]));
        panel.add(campoCategoria);

        panel.add(new JLabel("Data:"));

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');

            campoData = new JFormattedTextField(mascaraData); // 2. Instanciar dentro do try
            campoData.setColumns(10);

            panel.add(campoData);
        } catch (ParseException er) {
            JOptionPane.showMessageDialog(null, "Erro ao armazenar a data. Tente novamente!");
            return;
        }

        panel.add(new JLabel("Descrição:"));
        JTextField campoDescricao = new JTextField();
        panel.add(campoDescricao);

        telaLancamento.add(panel, BorderLayout.CENTER);

        // botões
        JPanel botoesPanel = new JPanel();

        //Cria botão de salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(0, 168, 107));
        btnSalvar.setForeground(Color.white);

        //Cria botão de cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(215, 60, 60));
        btnCancelar.setForeground(Color.white);

        btnSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(campoValor.getText().isEmpty() ||
                        campoTipo.getSelectedIndex() == 0 ||
                        campoCategoria.getSelectedItem().equals("") ||
                        campoDescricao.getText().isEmpty() ||
                        campoData.getText().contains("_")){

                    if(Main.contaAtiva.getCategorias().isEmpty()) {

                        JOptionPane.showMessageDialog(null, "Não há categorias cadastrada. Adicionar uma categoria!");
                        categorias();
                        return;
                    }

                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(campoValor.getText().matches("\\d+")){

                    // Tenta converter para LocalDate
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    campoValor.setText(campoValor.getText().replace(',', '.'));
                    float valor = Float.parseFloat(campoValor.getText());

                    String selecionado = (String) campoCategoria.getSelectedItem();

                    try {
                        LocalDate dataConvertida = LocalDate.parse(campoData.getText(), formato);

                        if(campoTipo.getSelectedItem().equals("Receita")){
                            Main.contaAtiva.depositar(valor, dataConvertida, selecionado, campoDescricao.getText());
                        } else {
                            Main.contaAtiva.sacar(valor, dataConvertida, selecionado, campoDescricao.getText());
                        }

                        JOptionPane.showMessageDialog(null,
                                "Transação adicionada com sucesso!",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE);

                        telaLancamento.dispose();
                        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Informe apenas números para Valor!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(ev -> {
            telaLancamento.dispose();
            SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
        });

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnCancelar);

        telaLancamento.add(botoesPanel, BorderLayout.SOUTH);

        telaLancamento.setVisible(true);
    }

    private void consulta() {
        JFrame telaConsulta = new JFrame();
        telaConsulta.setVisible(true);

        telaConsulta.setTitle("Extrato");
        telaConsulta.setSize(600, 400);
        telaConsulta.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel filtrosPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Filtros
        panel.add(new JLabel("Data inicial:"));
        panel.add(new JLabel("Data final:"));

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');

            campoDataFiltroInicial = new JFormattedTextField(mascaraData);
            campoDataFiltroInicial.setColumns(10);

            campoDataFiltroFinal = new JFormattedTextField(mascaraData);
            campoDataFiltroFinal.setColumns(10);

            panel.add(campoDataFiltroInicial);
            panel.add(campoDataFiltroFinal);
        } catch (ParseException er) {
            JOptionPane.showMessageDialog(null, "Erro ao armazenar a data. Tente novamente!");
            return;
        }

        JLabel tipoLabel = new JLabel("Tipo:");
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Todos", "Receita", "Despesa"});

        JLabel LabelCategoria = new JLabel("Categoria:");
        List<String> categoriasConsulta = new ArrayList<>();
        categoriasConsulta.add("Todas"); // Adiciona somente na tela de consulta
        categoriasConsulta.addAll(Main.contaAtiva.getCategorias());
        JComboBox<String> campoCategoria = new JComboBox<String>(categoriasConsulta.toArray(new String[0]));
        panel.add(campoCategoria);

        filtrosPanel.add(new JLabel("Data inicial:"));
        filtrosPanel.add(campoDataFiltroInicial);
        filtrosPanel.add(new JLabel("Data final:"));
        filtrosPanel.add(campoDataFiltroFinal);
        filtrosPanel.add(tipoLabel);
        filtrosPanel.add(tipoCombo);
        filtrosPanel.add(LabelCategoria);
        filtrosPanel.add(campoCategoria);

        // Área de texto para resultados
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // Botão de consulta
        JButton consultarButton = criarBotao("Consultar", new Color(0, 120, 215));
        consultarButton.addActionListener(e -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inicio = campoDataFiltroInicial.getText().isEmpty() ? LocalDate.MIN : LocalDate.parse(campoDataFiltroInicial.getText(), formatter);
                LocalDate fim = campoDataFiltroFinal.getText().isEmpty() ? LocalDate.MAX : LocalDate.parse(campoDataFiltroFinal.getText(), formatter);

                String tipoSelecionado = (String) tipoCombo.getSelectedItem();
                String categoriaSelecionada = (String) campoCategoria.getSelectedItem();

                List<Character> tipos = new ArrayList<>();
                if (tipoSelecionado.equals("Todos") || tipoSelecionado.equals("Receita")) {
                    tipos.add('E');
                }
                if (tipoSelecionado.equals("Todos") || tipoSelecionado.equals("Despesa")) {
                    tipos.add('S');
                }

                resultadoArea.setText("Data | Categoria | Tipo | Descrição | Valor\n");

                for (Transacao transacao : Main.contaAtiva.getExtrato()) {
                    boolean validaData = (transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim))
                            || transacao.getData().equals(inicio)
                            || transacao.getData().equals(fim);

                    boolean validaTipo = tipos.contains(transacao.getTipo());

                    boolean validaCategoria = categoriaSelecionada.equals("Todas")
                            || transacao.getCategoria().equalsIgnoreCase(categoriaSelecionada);

                    if (validaData && validaTipo && validaCategoria) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String dataFormatada = dtf.format(transacao.getData());

                        // ✅ Aqui está o tipo formatado
                        String tipoFormatado = transacao.getTipo() == 'E' ? "Receita" : "Despesa";

                        String transacaoString = dataFormatada + " - " + transacao.getCategoria() + " - " +
                                tipoFormatado + " - " + transacao.getDescricao() + " - R$" + transacao.getValor();
                        resultadoArea.append(transacaoString + "\n");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });



        JButton cancelButton = criarBotao("Cancelar", new Color(215, 60, 60));
        cancelButton.addActionListener(e -> {

            telaConsulta.dispose();
            SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
        });

        JPanel panelBotao = new JPanel();
        panelBotao.add(consultarButton);
        panelBotao.add(cancelButton);

        panel.add(filtrosPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotao, BorderLayout.SOUTH);

        telaConsulta.add(panel);
    }

    private void categorias() {

        JFrame telaCategorias = new JFrame();
        telaCategorias.setVisible(true);

        telaCategorias.setTitle("Gerenciar Categorias");
        telaCategorias.setSize(400, 300);
        telaCategorias.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBotao = new JPanel();

        JList<String> categorias = new JList<>(Main.contaAtiva.getCategorias().toArray(new String[0]));
        DefaultListModel<String> listModel = new DefaultListModel<>();

        JButton btnAdicionar = criarBotao("Adicionar", new Color(0, 168, 107));
        btnAdicionar.addActionListener(e -> {

            String novaCategoria = "";

            do {

                novaCategoria = JOptionPane.showInputDialog(this, "Digite o nome da nova categoria:");

                if(novaCategoria == null){
                    return;
                }

                novaCategoria = novaCategoria.trim();

            } while (novaCategoria.isEmpty());

            Main.contaAtiva.adicionarCategoria(novaCategoria);

            telaCategorias.dispose();
            categorias();

        });

        JButton btnEditar = criarBotao("Editar", new Color(0, 120, 215));
        btnEditar.addActionListener(er -> {

                    int selectedIndex = categorias.getSelectedIndex();

                    if (selectedIndex != -1) {

                        String categoriaAtual = categorias.getSelectedValue();
                        String novaCategoria;

                        do {

                            novaCategoria = JOptionPane.showInputDialog(this, "Editar categoria:", categoriaAtual);

                            if (novaCategoria == null) {
                                return;
                            }

                            novaCategoria = novaCategoria.trim();

                        } while (novaCategoria.isEmpty());

                        if (!novaCategoria.equals(categoriaAtual)) {

                            Main.contaAtiva.editarCategoria(categorias.getSelectedValue(), novaCategoria.trim());

                            listModel.clear();
                            Main.contaAtiva.getCategorias().forEach(listModel::addElement);

                            telaCategorias.dispose();
                            categorias();
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Selecione uma categoria para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
        });

        JButton btnRemover = criarBotao("Remover", new Color(215, 60, 60));
        btnRemover.addActionListener(err -> {

            int selectedIndex = categorias.getSelectedIndex();

            if (selectedIndex != -1) {

                try {
                    Main.contaAtiva.removerCategoria(categorias.getSelectedValue());

                    listModel.clear();
                    Main.contaAtiva.getCategorias().forEach(listModel::addElement);

                    telaCategorias.dispose();
                    categorias();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria para remover", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });


        panelBotao.add(btnAdicionar);
        panelBotao.add(btnEditar);
        panelBotao.add(btnRemover);

        // Lista de categorias
        JScrollPane scrollPane = new JScrollPane(categorias);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(panelBotao, BorderLayout.SOUTH);

        telaCategorias.add(panel);
    }
}
