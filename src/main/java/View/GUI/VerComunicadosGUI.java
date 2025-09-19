package View.GUI;

import DAO.ComunicadoDAO;
import Model.Comunicado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VerComunicadosGUI extends JFrame {

    private JList<Comunicado> listaComunicados;
    private DefaultListModel<Comunicado> listModel;
    private JTextArea areaMensagem; // Renomeado para clareza
    private JLabel lblTituloDetalhe;
    private JLabel lblDataDetalhe;
    private ComunicadoDAO comunicadoDAO;

    public VerComunicadosGUI() {
        comunicadoDAO = new ComunicadoDAO();
        setTitle("Mural de Comunicados");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        carregarComunicados();
    }

    private void initComponents() {
        // --- PAINEL ESQUERDO (LISTA DE TÍTULOS) ---
        listModel = new DefaultListModel<>();
        listaComunicados = new JList<>(listModel);
        listaComunicados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaComunicados.setCellRenderer(new ComunicadoListRenderer()); // Customiza a aparência

        JScrollPane listaScrollPane = new JScrollPane(listaComunicados);
        listaScrollPane.setBorder(new TitledBorder("Comunicados Recentes"));

        // --- PAINEL DIREITO (DETALHES DO COMUNICADO) ---
        JPanel painelDetalhes = new JPanel(new BorderLayout(10, 10));
        painelDetalhes.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setLayout(new BoxLayout(painelCabecalho, BoxLayout.Y_AXIS));
        lblTituloDetalhe = new JLabel("Selecione um comunicado para ler");
        lblTituloDetalhe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblDataDetalhe = new JLabel();
        lblDataDetalhe.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        painelCabecalho.add(lblTituloDetalhe);
        painelCabecalho.add(lblDataDetalhe);

        areaMensagem = new JTextArea();
        areaMensagem.setEditable(false);
        areaMensagem.setWrapStyleWord(true);
        areaMensagem.setLineWrap(true);
        areaMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane conteudoScrollPane = new JScrollPane(areaMensagem);
        conteudoScrollPane.setBorder(new TitledBorder("Mensagem")); // Título do painel corrigido

        painelDetalhes.add(painelCabecalho, BorderLayout.NORTH);
        painelDetalhes.add(conteudoScrollPane, BorderLayout.CENTER);

        // --- DIVISOR (JSplitPane) ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listaScrollPane, painelDetalhes);
        splitPane.setDividerLocation(250);

        add(splitPane, BorderLayout.CENTER);

        // --- EVENTO DE SELEÇÃO NA LISTA ---
        listaComunicados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Comunicado selecionado = listaComunicados.getSelectedValue();
                if (selecionado != null) {
                    exibirDetalhes(selecionado);
                }
            }
        });
    }

    // CORREÇÃO: Este método agora popula a JList, não uma JTable.
    private void carregarComunicados() {
        listModel.clear(); // Limpa o modelo da lista
        List<Comunicado> comunicados = comunicadoDAO.listarTodosComunicados();
        for (Comunicado comunicado : comunicados) {
            listModel.addElement(comunicado); // Adiciona cada objeto Comunicado à lista
        }

        if (!listModel.isEmpty()) {
            listaComunicados.setSelectedIndex(0); // Seleciona o primeiro item por padrão
        }
    }

    private void exibirDetalhes(Comunicado comunicado) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Publicado em' dd/MM/yyyy 'às' HH:mm");
        lblTituloDetalhe.setText(comunicado.getTitulo());
        lblDataDetalhe.setText(comunicado.getDataPublicacao().format(formatter));
        // CORREÇÃO: Usando getMensagem() para preencher a área de texto.
        areaMensagem.setText(comunicado.getMensagem());
        areaMensagem.setCaretPosition(0); // Garante que o texto comece do topo
    }

    // Classe interna para customizar a exibição dos itens na JList
    private static class ComunicadoListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Comunicado) {
                Comunicado comunicado = (Comunicado) value;
                setText(comunicado.getTitulo()); // Mostra apenas o título na lista
                setBorder(new EmptyBorder(5, 5, 5, 5));
            }
            return this;
        }
    }
}