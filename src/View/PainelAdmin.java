package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import View.GerenciarUsuariosGUI;
import View.VerReservasGUI;
import View.PublicarComunicadoGUI;
import View.TelaGestaoAreas;

public class PainelAdmin extends JFrame {
    private int usuarioId;

    public PainelAdmin(int usuarioId) {
        this.usuarioId = usuarioId;

=======

public class PainelAdmin extends JFrame {

    public PainelAdmin() {
>>>>>>> e871ee0f739b30477297ded30f3dd0b8a076ea22
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

<<<<<<< HEAD
        // Lógica dos Botões
=======
        // Adicionando a ação ao Botão de Gerenciar Usuários
>>>>>>> e871ee0f739b30477297ded30f3dd0b8a076ea22
        btnGerenciarUsuarios.addActionListener(e -> {
            GerenciarUsuariosGUI telaUsuarios = new GerenciarUsuariosGUI();
            telaUsuarios.setVisible(true);
        });

<<<<<<< HEAD
=======
        // NOVO: Adicionando ação ao Botão de Gerenciar Áreas
>>>>>>> e871ee0f739b30477297ded30f3dd0b8a076ea22
        btnGerenciarAreas.addActionListener(e -> {
            TelaGestaoAreas telaGestaoAreas = new TelaGestaoAreas();
            telaGestaoAreas.setVisible(true);
        });

<<<<<<< HEAD
        btnVerReservas.addActionListener(e -> {
            VerReservasGUI telaReservas = new VerReservasGUI();
            telaReservas.setVisible(true);
        });

        btnPublicarComunicado.addActionListener(e -> {
            PublicarComunicadoGUI telaPublicar = new PublicarComunicadoGUI(this.usuarioId);
            telaPublicar.setVisible(true);
        });

        // Adicionando os botões ao painel
=======
>>>>>>> e871ee0f739b30477297ded30f3dd0b8a076ea22
        painelPrincipal.add(btnGerenciarUsuarios);
        painelPrincipal.add(btnGerenciarAreas);
        painelPrincipal.add(btnVerReservas);
        painelPrincipal.add(btnPublicarComunicado);

<<<<<<< HEAD
        add(painelPrincipal);
=======
        add(painelPrincipal); // Removido "Component add"
>>>>>>> e871ee0f739b30477297ded30f3dd0b8a076ea22
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
<<<<<<< HEAD
            // Para testar, use um ID de usuário fictício (ex: 1)
            PainelAdmin painel = new PainelAdmin(1);
            painel.setVisible(true);
        });
    }
}
=======
            PainelAdmin painel = new PainelAdmin();
            painel.setVisible(true);
        });
    }
}
>>>>>>> e871ee0f739b30477297ded30f3dd0b8a076ea22
