package View.GUI;

import DAO.ReservaDAO;
import Model.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VerAgendamentosGUI extends JFrame {

    private JTable tabelaAgendamentos;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar;

    public VerAgendamentosGUI() {
        setTitle("Agendamentos do Dia");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Criação da tabela
        String[] colunas = {"ID", "Área Comum ID", "Data", "Horário Início", "Horário Fim", "Status"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaAgendamentos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaAgendamentos);

        // Adiciona a tabela ao painel central
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        btnAtualizar = new JButton("Atualizar Lista");
        painelBotoes.add(btnAtualizar);

        add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona a ação ao botão de atualizar
        btnAtualizar.addActionListener(e -> carregarAgendamentos());

        // Carrega os agendamentos ao iniciar a tela
        carregarAgendamentos();
    }

    private void carregarAgendamentos() {
        tableModel.setRowCount(0); // Limpa a tabela antes de carregar
        ReservaDAO reservaDAO = new ReservaDAO();
        List<Reserva> reservas = reservaDAO.listarTodasReservas(); // Poderíamos filtrar só os de hoje aqui

        for (Reserva reserva : reservas) {
            Object[] linha = {
                    reserva.getId(),
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
