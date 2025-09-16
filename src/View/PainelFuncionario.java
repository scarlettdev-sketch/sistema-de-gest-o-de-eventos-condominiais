package View;

import javax.swing.*;
import java.awt.*;
import View.VerComunicadosGUI;

public class PainelFuncionario extends JFrame {
    private int usuarioId;

    public PainelFuncionario(int usuarioId) {
        this.usuarioId = usuarioId;

        setTitle("Painel do Funcionário");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(2, 1, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botões
        JButton btnVerComunicados = new JButton("Ver Comunicados");
        JButton btnVerAgendamentos = new JButton("Ver Agendamentos do Dia");

        // Adicionando a lógica aos botões
        btnVerComunicados.addActionListener(e -> {
            VerComunicadosGUI telaComunicados = new VerComunicadosGUI();
            telaComunicados.setVisible(true);
        });

        painelPrincipal.add(btnVerComunicados);
        painelPrincipal.add(btnVerAgendamentos);

        add(painelPrincipal);

        // Lógica para o botão "Ver Agendamentos do Dia"
        btnVerAgendamentos.addActionListener(e -> {
            VerAgendamentosGUI telaAgendamentos = new VerAgendamentosGUI();
            telaAgendamentos.setVisible(true);
        });
    }
}
