package View; // ou View.GUI

import View.GUI.VerComunicadosGUI;

import javax.swing.*;
import java.awt.*;

public class PainelFuncionario extends JFrame {
    private int usuarioId;

    public PainelFuncionario(int usuarioId) {
        this.usuarioId = usuarioId;

        setTitle("Painel do Funcionário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridLayout(2, 1, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botões
        JButton btnVerComunicados = new JButton("Ver Mural de Comunicados");
        JButton btnVerAgenda = new JButton("Ver Agenda de Reservas");

        // Ações dos Botões
        // Reutilizamos a MESMA tela de comunicados do morador, pois ela é perfeita para leitura.
        btnVerComunicados.addActionListener(e -> new VerComunicadosGUI().setVisible(true));

        // Chamamos a nova tela de Agenda que criaremos no próximo passo.
        btnVerAgenda.addActionListener(e -> new View.AgendaGeralGUI().setVisible(true));


        // Adicionando os botões ao painel
        painelPrincipal.add(btnVerComunicados);
        painelPrincipal.add(btnVerAgenda);

        add(painelPrincipal);
    }
}