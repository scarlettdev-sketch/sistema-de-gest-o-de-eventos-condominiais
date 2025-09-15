package View;

import DAO.AreaComumDAO;
import Model.AreaComum;

import javax.swing.*;
import java.awt.*;
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

        // Novo campo para a descrição
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

        // Chamando o método para carregar as áreas comuns
        carregarAreasComuns();
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
}