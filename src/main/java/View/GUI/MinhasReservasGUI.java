package View.GUI;

import DAO.ReservaDAO;
import Model.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MinhasReservasGUI extends JFrame {

    private int usuarioId;
    private JTable tabelaReservas;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar;

    public MinhasReservasGUI(int usuarioId){
        this.usuarioId = usuarioId;

        setTitle("Minhas Reservas");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Criação de Tabela

        String[] colunas = {"ID", "Área Comum ID", "Data", "Horário Inicio", "Horário Fim", "Status", "Descrição"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaReservas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaReservas);

        // Adiciona a tabela ao painel central
        add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões
        JPanel painelBotoes = new JPanel();
        btnAtualizar = new JButton("Atualizar Lista");
        painelBotoes.add(btnAtualizar);

        add(painelBotoes, BorderLayout.SOUTH);

        //Adiciona a ação do botão de atualizar
        btnAtualizar.addActionListener(e -> carregarMinhasReservas());

        //Carrega as reservas ao iniciar a tela
        carregarMinhasReservas();
    }

    private void carregarMinhasReservas() {
        tableModel.setRowCount(0); // Limpa a tabela antes de carregar
        ReservaDAO reservaDAO = new ReservaDAO();
        List<Reserva> reservas = reservaDAO.listarReservasPorUsuarioId(this.usuarioId);

        for (Reserva reserva : reservas){
            Object[] linha = {
                    reserva.getId(),
                    reserva.getAreaComumId(),
                    reserva.getData(),
                    reserva.getHorarioInicio(),
                    reserva.getHorarioFim(),
                    reserva.getStatus(),
                    reserva.getDescricao()
            };
            tableModel.addRow(linha);
        }
    }
}
