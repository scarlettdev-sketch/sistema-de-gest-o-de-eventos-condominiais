package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelAdmin extends JFrame {

    public PainelAdmin() {
        setTitle("Painel do Admnistrador");
        setSize(500, 400); // Tamanho da Janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(4, 1, 10, 10)); // Layout em grade para os botões
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna

        //Botões
        JButton btnGerenciarUsuarios = new JButton("Gerenciar Usuários");
        JButton btnGerenciarAreas = new JButton("Gerenciar Áreas");
        JButton btnVerReservas = new JButton("Ver Reservas");
        JButton btnPulicarComunicado = new JButton("Publicar Comunicado");

        //Adicionando a ação ao Botão de Gerenciar Usuários
        btnGerenciarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Instancia a nova tela e a torna visível
                GerenciarUsuariosGUI telaUsuarios = new GerenciarUsuariosGUI();
                telaUsuarios.setVisible(true);
            }
        });

        painelPrincipal.add(btnGerenciarUsuarios);
        painelPrincipal.add(btnGerenciarAreas);
        painelPrincipal.add(btnVerReservas);
        painelPrincipal.add(btnPulicarComunicado);

        Component add = add(painelPrincipal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PainelAdmin painel = new PainelAdmin();
            painel.setVisible(true);
        });
    }
}
