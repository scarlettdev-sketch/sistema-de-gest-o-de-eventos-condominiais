package View.GUI;

import DAO.AreaComumDAO;
import Model.RelatorioUsoArea;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RelatoriosGUI extends JFrame {

    private JTable tabelaRelatorio;
    private DefaultTableModel tableModel;
    private AreaComumDAO areaComumDAO;

    public RelatoriosGUI() {
        areaComumDAO = new AreaComumDAO();
        setTitle("Relatório - Uso de Áreas Comuns");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Título no topo
        JLabel lblTitulo = new JLabel("Estatísticas de Uso por Área Comum", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabela de Relatórios
        String[] colunas = {"Área Comum", "Total de Reservas (Aprovadas)", "Valor Arrecadado (R$)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaRelatorio = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaRelatorio);
        add(scrollPane, BorderLayout.CENTER);

        // Botão para atualizar
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAtualizar = new JButton("Atualizar Relatório");
        btnAtualizar.addActionListener(e -> carregarRelatorio());
        painelBotao.add(btnAtualizar);
        add(painelBotao, BorderLayout.SOUTH);

        // Carrega os dados ao abrir a tela
        carregarRelatorio();
    }

    private void carregarRelatorio() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<RelatorioUsoArea> relatorio = areaComumDAO.gerarRelatorioUso();

        for (RelatorioUsoArea item : relatorio) {
            Object[] linha = {
                    item.getNomeArea(),
                    item.getTotalReservas(),
                    String.format("%.2f", item.getValorArrecadado()) // Formata o valor como moeda
            };
            tableModel.addRow(linha);
        }
    }
}