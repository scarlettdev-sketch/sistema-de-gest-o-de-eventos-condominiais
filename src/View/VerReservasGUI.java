package View;

import DAO.ReservaDAO;
import Model.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VerReservasGUI extends JFrame {

    private JTable tabelaReservas;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar;
    private JButton btnAprovar;
    private JButton btnRejeitar;

    public VerReservasGUI() {
        setTitle("Visualizar Reservas");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Usuário ID", "Área Comum ID", "Data", "Horário Início", "Horário Fim", "Status"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaReservas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaReservas);

        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        btnAtualizar = new JButton("Atualizar Lista");
        btnAprovar = new JButton("Aprovar Reserva");
        btnRejeitar = new JButton("Rejeitar Reserva");

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnAprovar);
        painelBotoes.add(btnRejeitar);

        add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarReservas());

        btnAprovar.addActionListener(e -> {
            int linhaSelecionada = tabelaReservas.getSelectedRow();
            if (linhaSelecionada != -1) {
                int idReserva = (int) tableModel.getValueAt(linhaSelecionada, 0);
                ReservaDAO reservaDAO = new ReservaDAO();
                reservaDAO.atualizarStatusReserva(idReserva, "Aprovada");
                carregarReservas();
                JOptionPane.showMessageDialog(this, "Reserva aprovada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma reserva para aprovar.");
            }
        });

        btnRejeitar.addActionListener(e -> {
            int linhaSelecionada = tabelaReservas.getSelectedRow();
            if (linhaSelecionada != -1) {
                int idReserva = (int) tableModel.getValueAt(linhaSelecionada, 0);
                ReservaDAO reservaDAO = new ReservaDAO();
                reservaDAO.atualizarStatusReserva(idReserva, "Rejeitada");
                carregarReservas();
                JOptionPane.showMessageDialog(this, "Reserva rejeitada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma reserva para rejeitar.");
            }
        });

        carregarReservas();
    }

    private void carregarReservas() {
        tableModel.setRowCount(0);
        ReservaDAO reservaDAO = new ReservaDAO();
        List<Reserva> reservas = reservaDAO.listarTodasReservas();

        for (Reserva reserva : reservas) {
            Object[] linha = {
                    reserva.getId(),
                    reserva.getUsuarioId(),
                    reserva.getAreaComumId(),
                    reserva.getData(),
                    reserva.getHorarioInicio(),
                    reserva.getHorarioFim(),
                    reserva.getStatus()
            };
            tableModel.addRow(linha);
        }
    }
}