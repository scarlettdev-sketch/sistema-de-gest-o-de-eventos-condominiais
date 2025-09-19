package View; // ou View.GUI

import DAO.ReservaDAO;
import Model.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AgendaGeralGUI extends JFrame {

    private JTable tabelaReservas;
    private DefaultTableModel tableModel;

    public AgendaGeralGUI() {
        setTitle("Agenda Geral de Ocupação das Áreas");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"ID Reserva", "Usuário ID", "Área ID", "Data", "Início", "Fim", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaReservas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaReservas);
        add(scrollPane, BorderLayout.CENTER);

        // Painel SUL com botão de atualizar
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAtualizar = new JButton("Atualizar Agenda");
        btnAtualizar.addActionListener(e -> carregarReservas());
        painelBotao.add(btnAtualizar);
        add(painelBotao, BorderLayout.SOUTH);

        carregarReservas();
    }

    private void carregarReservas() {
        tableModel.setRowCount(0);
        ReservaDAO reservaDAO = new ReservaDAO();
        // Reutilizamos o método que lista TODAS as reservas, o mesmo do admin.
        List<Reserva> reservas = reservaDAO.listarTodasReservas();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Reserva reserva : reservas) {
            // Mostramos apenas as reservas que estão confirmadas
            if (reserva.getStatus().equalsIgnoreCase("aprovada")) {
                Object[] linha = {
                        reserva.getId(),
                        reserva.getUsuarioId(),
                        reserva.getAreaComumId(),
                        reserva.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        reserva.getHorarioInicio().format(timeFormatter),
                        reserva.getHorarioFim().format(timeFormatter),
                        reserva.getStatus()
                };
                tableModel.addRow(linha);
            }
        }
    }
}