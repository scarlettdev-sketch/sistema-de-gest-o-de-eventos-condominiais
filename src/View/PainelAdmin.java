package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.GerenciarUsuariosGUI;
import View.VerReservasGUI;
import View.PublicarComunicadoGUI;
import View.TelaGestaoAreas;

public class PainelAdmin extends JFrame {
    private int usuarioId;

    public PainelAdmin(int usuarioId) {
        this.usuarioId = usuarioId;

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

        // Lógica dos Botões
        btnGerenciarUsuarios.addActionListener(e -> {
            GerenciarUsuariosGUI telaUsuarios = new GerenciarUsuariosGUI();
            telaUsuarios.setVisible(true);
        });

        btnGerenciarAreas.addActionListener(e -> {
            TelaGestaoAreas telaGestaoAreas = new TelaGestaoAreas();
            telaGestaoAreas.setVisible(true);
        });

        btnVerReservas.addActionListener(e -> {
            VerReservasGUI telaReservas = new VerReservasGUI();
            telaReservas.setVisible(true);
        });

        btnPublicarComunicado.addActionListener(e -> {
            PublicarComunicadoGUI telaPublicar = new PublicarComunicadoGUI(this.usuarioId);
            telaPublicar.setVisible(true);
        });

        // Adicionando os botões ao painel
        painelPrincipal.add(btnGerenciarUsuarios);
        painelPrincipal.add(btnGerenciarAreas);
        painelPrincipal.add(btnVerReservas);
        painelPrincipal.add(btnPublicarComunicado);

        add(painelPrincipal);
    }

    public PainelAdmin() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Para testar, use um ID de usuário fictício (ex: 1)
            PainelAdmin painel = new PainelAdmin(1);
            painel.setVisible(true);
        });
    }
}