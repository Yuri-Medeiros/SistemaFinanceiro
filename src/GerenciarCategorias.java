import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciarCategorias extends JFrame {
    private final Conta conta;
    private final DefaultListModel<String> listModel;
    private final JList<String> categoriasList;

    public GerenciarCategorias(Conta conta) {
        this.conta = conta;
        this.listModel = new DefaultListModel<>();
        this.categoriasList = new JList<>(listModel);

        initUI();
        atualizarListaCategorias();
    }

    private void initUI() {
        setTitle("Gerenciar Categorias");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lista de categorias
        JScrollPane scrollPane = new JScrollPane(categoriasList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel botoesPanel = criarPainelBotoes();
        panel.add(botoesPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private JPanel criarPainelBotoes() {
        JPanel botoesPanel = new JPanel();

        JButton btnAdicionar = criarBotao("Adicionar", Color.GREEN.darker());
        btnAdicionar.addActionListener(this::adicionarCategoria);

        JButton btnEditar = criarBotao("Editar", Color.BLUE);
        btnEditar.addActionListener(this::editarCategoria);

        JButton btnRemover = criarBotao("Remover", Color.RED.darker());
        btnRemover.addActionListener(this::removerCategoria);

        botoesPanel.add(btnAdicionar);
        botoesPanel.add(btnEditar);
        botoesPanel.add(btnRemover);

        return botoesPanel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        return botao;
    }

    private void atualizarListaCategorias() {
        listModel.clear();
        conta.getCategorias().forEach(listModel::addElement);
    }

    private void adicionarCategoria(ActionEvent e) {
        String novaCategoria = JOptionPane.showInputDialog(this, "Digite o nome da nova categoria:");
        if (novaCategoria != null && !novaCategoria.trim().isEmpty()) {
            try {
                conta.adicionarCategoria(novaCategoria.trim());
                atualizarListaCategorias();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarCategoria(ActionEvent e) {
        int selectedIndex = categoriasList.getSelectedIndex();
        if (selectedIndex != -1) {
            String categoriaAtual = listModel.getElementAt(selectedIndex);
            String novaCategoria = JOptionPane.showInputDialog(this, "Editar categoria:", categoriaAtual);
            if (novaCategoria != null && !novaCategoria.trim().isEmpty()) {
                try {
                    conta.editarCategoria(selectedIndex, novaCategoria.trim());
                    atualizarListaCategorias();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerCategoria(ActionEvent e) {
        int selectedIndex = categoriasList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                conta.removerCategoria(selectedIndex);
                atualizarListaCategorias();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria para remover", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}