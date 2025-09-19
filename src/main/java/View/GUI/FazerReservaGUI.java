package View.GUI;

import DAO.AreaComumDAO;
import DAO.ReservaDAO;
import Model.AreaComum;
import Model.Reserva;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FazerReservaGUI extends JFrame {

    private int usuarioId;
    private JComboBox<String> comboAreas;
    private JTextField txtData;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFim;
    private JTextArea txtDescricao;
    private JButton btnReservar;

    public FazerReservaGUI(int usuarioId) {
        this.usuarioId = usuarioId;

        setTitle("Fazer Nova Reserva");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Rótulos e Campos
        gbc.gridx = 0; gbc.gridy = 0;
        painelPrincipal.add(new JLabel("Área Comum:"), gbc);
        comboAreas = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        painelPrincipal.add(comboAreas, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        painelPrincipal.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        txtData = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        painelPrincipal.add(txtData, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        painelPrincipal.add(new JLabel("Hora de Início (HH:mm):"), gbc);
        txtHoraInicio = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        painelPrincipal.add(txtHoraInicio, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        painelPrincipal.add(new JLabel("Hora de Fim (HH:mm):"), gbc);
        txtHoraFim = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        painelPrincipal.add(txtHoraFim, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        painelPrincipal.add(new JLabel("Descrição do Evento:"), gbc);
        txtDescricao = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(txtDescricao);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        painelPrincipal.add(scrollPane, gbc);

        btnReservar = new JButton("Solicitar Reserva");
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(btnReservar, gbc);

        add(painelPrincipal);

        carregarAreasComuns();
        adicionarEventoBotaoReservar();
    }

    private void carregarAreasComuns() {
        AreaComumDAO areaDAO = new AreaComumDAO();
        List<AreaComum> areas = areaDAO.listarAreasAtivas();

        if (areas != null && !areas.isEmpty()) {
            for (AreaComum area : areas) {
                comboAreas.addItem(area.getNome());
            }
        } else {
            comboAreas.addItem("Nenhuma área encontrada");
            comboAreas.setEnabled(false);
        }
    }

    private void adicionarEventoBotaoReservar() {
        btnReservar.addActionListener(e -> {
            try {
                // --- COLETA E VALIDAÇÃO DOS DADOS ---
                String nomeArea = (String) comboAreas.getSelectedItem();
                if (nomeArea == null || nomeArea.equals("Nenhuma área encontrada") || nomeArea.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, selecione uma área válida.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                LocalDate data = LocalDate.parse(txtData.getText(), dateFormatter);
                LocalTime horaInicio = LocalTime.parse(txtHoraInicio.getText(), timeFormatter);
                LocalTime horaFim = LocalTime.parse(txtHoraFim.getText(), timeFormatter);
                String descricao = txtDescricao.getText();

                if (horaFim.isBefore(horaInicio) || horaFim.equals(horaInicio)) {
                    JOptionPane.showMessageDialog(this, "O horário de término deve ser posterior ao horário de início.", "Erro de ValidaÇÃO", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // ---> INÍCIO DA LÓGICA DE VERIFICAÇÃO DE CONFLITO <---
                AreaComumDAO areaDAO = new AreaComumDAO();
                // Assumindo que você tenha ou crie este método em AreaComumDAO
                int areaId = areaDAO.buscarIdPorNome(nomeArea);

                ReservaDAO reservaDAO = new ReservaDAO();
                if (reservaDAO.existeConflitoDeHorario(areaId, data, horaInicio, horaFim)) {
                    // Se o método retornar true, há um conflito. Avisamos o usuário e paramos a execução.
                    JOptionPane.showMessageDialog(this,
                            "Conflito de agendamento detectado!\nJá existe uma reserva para esta área que conflita com o horário solicitado.",
                            "Horário Indisponível", JOptionPane.WARNING_MESSAGE);
                    return; // Impede que o código de salvar a reserva seja executado
                }
                // ---> FIM DA LÓGICA DE VERIFICAÇÃO DE CONFLITO <---

                // --- SE NÃO HOUVER CONFLITO, PROSSEGUIMOS COM A RESERVA ---
                Reserva novaReserva = new Reserva(usuarioId, areaId, data, horaInicio, horaFim, "pendente", descricao);

                if (reservaDAO.salvarReserva(novaReserva)) {
                    JOptionPane.showMessageDialog(this, "Reserva solicitada com sucesso! Aguardando aprovação do administrador.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose(); // Fecha a janela após o sucesso
                } else {
                    JOptionPane.showMessageDialog(this, "Ocorreu um erro ao salvar sua solicitação de reserva.", "Erro no Servidor", JOptionPane.ERROR_MESSAGE);
                }

            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data ou hora inválido.\nUse dd/MM/yyyy para datas e HH:mm para horas.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}