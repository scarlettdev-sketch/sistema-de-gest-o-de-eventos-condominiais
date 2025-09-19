package View.GUI;

import DAO.ReservaDAO;
import Model.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VerReservasGUI extends JFrame {

    private JTable tabelaReservas;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar;
    private JButton btnAprovar;
    private JButton btnRejeitar;

    public VerReservasGUI() {
        setTitle("Administração de Reservas");
        setSize(1200, 700); // Aumentei o tamanho para caber as novas colunas
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Tabela com mais detalhes
        String[] colunas = {"ID", "Usuário ID", "Área ID", "Data", "Início", "Fim", "Status", "Descrição (Morador)", "Motivo Rejeição"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Células não editáveis
            }
        };
        tabelaReservas = new JTable(tableModel);
        tabelaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaReservas);

        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAtualizar = new JButton("Atualizar Lista");
        btnAprovar = new JButton("Aprovar Reserva");
        btnRejeitar = new JButton("Rejeitar Reserva");

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnAprovar);
        painelBotoes.add(btnRejeitar);

        add(painelBotoes, BorderLayout.SOUTH);

        // --- AÇÕES DOS BOTÕES ---

        btnAtualizar.addActionListener(e -> carregarReservas());

        btnAprovar.addActionListener(e -> aprovarReservaSelecionada());

        btnRejeitar.addActionListener(e -> rejeitarReservaSelecionada());

        carregarReservas();
    }

    private void carregarReservas() {
        tableModel.setRowCount(0); // Limpa a tabela
        ReservaDAO reservaDAO = new ReservaDAO();
        List<Reserva> reservas = reservaDAO.listarTodasReservas();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Reserva reserva : reservas) {
            Object[] linha = {
                    reserva.getId(),
                    reserva.getUsuarioId(),
                    reserva.getAreaComumId(),
                    reserva.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    reserva.getHorarioInicio().format(timeFormatter),
                    reserva.getHorarioFim().format(timeFormatter),
                    reserva.getStatus(),
                    reserva.getDescricao(),
                    reserva.getMotivoRejeicao() // Exibindo o novo campo
            };
            tableModel.addRow(linha);
        }
    }

    private void aprovarReservaSelecionada() {
        int linhaSelecionada = tabelaReservas.getSelectedRow();
        if (linhaSelecionada != -1) {
            int idReserva = (int) tableModel.getValueAt(linhaSelecionada, 0);
            String statusAtual = (String) tableModel.getValueAt(linhaSelecionada, 6);

            if (!statusAtual.equalsIgnoreCase("pendente")) {
                JOptionPane.showMessageDialog(this, "Apenas reservas com status 'pendente' podem ser aprovadas.", "Ação Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ReservaDAO reservaDAO = new ReservaDAO();
            // APROVADO foi corrigido para aprovada no schema.sql
            reservaDAO.atualizarStatusReserva(idReserva, "aprovada");
            carregarReservas();
            JOptionPane.showMessageDialog(this, "Reserva aprovada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma reserva para aprovar.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void rejeitarReservaSelecionada() {
        int linhaSelecionada = tabelaReservas.getSelectedRow();
        if (linhaSelecionada != -1) {
            int idReserva = (int) tableModel.getValueAt(linhaSelecionada, 0);
            String statusAtual = (String) tableModel.getValueAt(linhaSelecionada, 6);

            if (!statusAtual.equalsIgnoreCase("pendente")) {
                JOptionPane.showMessageDialog(this, "Apenas reservas com status 'pendente' podem ser rejeitadas.", "Ação Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- NOVA LÓGICA COM INPUT DIALOG ---
            String motivo = JOptionPane.showInputDialog(this,
                    "Por favor, insira o motivo da rejeição:",
                    "Rejeitar Reserva",
                    JOptionPane.PLAIN_MESSAGE);

            // Se o usuário clicou "OK" e escreveu algo
            if (motivo != null && !motivo.trim().isEmpty()) {
                ReservaDAO reservaDAO = new ReservaDAO();
                if (reservaDAO.rejeitarReserva(idReserva, motivo.trim())) {
                    carregarReservas();
                    JOptionPane.showMessageDialog(this, "Reserva rejeitada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar o banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else if (motivo != null) { // Clicou OK mas não escreveu nada
                JOptionPane.showMessageDialog(this, "O motivo da rejeição não pode ser vazio.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
            }
            // Se o usuário clicou "Cancelar" (motivo == null), nada acontece.

        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma reserva para rejeitar.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
        }
    }
}