package View;

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
        gbc.gridx = 1; gbc.gridy = 0;
        painelPrincipal.add(comboAreas, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelPrincipal.add(new JLabel("Data:"), gbc);
        txtData = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        painelPrincipal.add(txtData, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelPrincipal.add(new JLabel("Hora de Início:"), gbc);
        txtHoraInicio = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2;
        painelPrincipal.add(txtHoraInicio, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painelPrincipal.add(new JLabel("Hora de Fim:"), gbc);
        txtHoraFim = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3;
        painelPrincipal.add(txtHoraFim, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        painelPrincipal.add(new JLabel("Descrição:"), gbc);
        txtDescricao = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(txtDescricao);
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        painelPrincipal.add(scrollPane, gbc);

        btnReservar = new JButton("Reservar");
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
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
        }
    }

    private void adicionarEventoBotaoReservar() {
        btnReservar.addActionListener(e -> {
            try {
                String nomeArea = (String) comboAreas.getSelectedItem();

                // CORREÇÃO: Variáveis são declaradas uma vez e usadas aqui
                String dataStr = txtData.getText();
                String horaInicioStr = txtHoraInicio.getText();
                String horaFimStr = txtHoraFim.getText();
                String descricao = txtDescricao.getText();

                // Lógica de parsing para os formatos corretos
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(dataStr, dateFormatter);

                LocalTime horaInicio = LocalTime.parse(horaInicioStr);
                LocalTime horaFim = LocalTime.parse(horaFimStr);

                if (nomeArea == null || nomeArea.isEmpty() || data == null || horaInicio == null || horaFim == null) {
                    JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AreaComumDAO areaDAO = new AreaComumDAO();
                int areaId = areaDAO.buscarIdPorNome(nomeArea);

                Reserva novaReserva = new Reserva(usuarioId, areaId, data, horaInicio, horaFim, "Pendente", descricao);

                ReservaDAO reservaDAO = new ReservaDAO();
                if (reservaDAO.salvarReserva(novaReserva)) {
                    JOptionPane.showMessageDialog(this, "Reserva solicitada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao solicitar reserva.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}