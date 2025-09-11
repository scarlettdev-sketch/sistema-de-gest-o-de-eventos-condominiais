package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelAdmin extends JFrame {

    public PainelAdmin() {
        setTitle("Painel do Administrador");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(4, 1, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botões
        JButton btnGerenciarUsuarios = new JButton("Gerenciar Usuários");
        JButton btnGerenciarAreas = new JButton("Gerenciar Áreas Comuns");
        JButton btnVerReservas = new JButton("Ver Reservas");
        JButton btnPublicarComunicado = new JButton("Publicar Comunicado");

        // Adicionando a ação ao Botão de Gerenciar Usuários
        btnGerenciarUsuarios.addActionListener(e -> {
            GerenciarUsuariosGUI telaUsuarios = new GerenciarUsuariosGUI();
            telaUsuarios.setVisible(true);
        });

        // NOVO: Adicionando ação ao Botão de Gerenciar Áreas
        btnGerenciarAreas.addActionListener(e -> {
            TelaGestaoAreas telaGestaoAreas = new TelaGestaoAreas();
            telaGestaoAreas.setVisible(true);
        });

        painelPrincipal.add(btnGerenciarUsuarios);
        painelPrincipal.add(btnGerenciarAreas);
        painelPrincipal.add(btnVerReservas);
        painelPrincipal.add(btnPublicarComunicado);

        add(painelPrincipal); // Removido "Component add"
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PainelAdmin painel = new PainelAdmin();
            painel.setVisible(true);
        });
    }
}
