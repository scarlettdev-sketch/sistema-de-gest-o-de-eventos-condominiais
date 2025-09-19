package View;

import View.GUI.GerenciarUsuariosGUI;
import View.GUI.PublicarComunicadoGUI;
import View.GUI.RelatoriosGUI;
import View.GUI.VerReservasGUI;

import javax.swing.*;
import java.awt.*;

// Removi imports não utilizados para limpar o código


public class PainelAdmin extends JFrame {
    private int usuarioId;

    public PainelAdmin(int usuarioId) {
        this.usuarioId = usuarioId;

        setTitle("Painel do Administrador");
        setSize(500, 450); // Aumentei um pouco a altura para o novo botão
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        // <<< MUDANÇA 1: Ajustar o layout para 5 linhas para o novo botão
        painelPrincipal.setLayout(new GridLayout(5, 1, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botões
        JButton btnGerenciarUsuarios = new JButton("Gerenciar Usuários");
        JButton btnGerenciarAreas = new JButton("Gerenciar Áreas Comuns");
        JButton btnVerReservas = new JButton("Ver Reservas");
        JButton btnPublicarComunicado = new JButton("Publicar Comunicado");
        // <<< MUDANÇA 2: Criar o novo botão de Relatórios
        JButton btnRelatorios = new JButton("Ver Relatórios");

        // Lógica dos Botões
        btnGerenciarUsuarios.addActionListener(e -> new GerenciarUsuariosGUI().setVisible(true));
        btnGerenciarAreas.addActionListener(e -> new TelaGestaoAreas().setVisible(true));
        btnVerReservas.addActionListener(e -> new VerReservasGUI().setVisible(true));
        btnPublicarComunicado.addActionListener(e -> new PublicarComunicadoGUI(this.usuarioId).setVisible(true));

        // <<< MUDANÇA 3: Adicionar a ação para o novo botão
        btnRelatorios.addActionListener(e -> new RelatoriosGUI().setVisible(true));

        // Adicionando os botões ao painel
        painelPrincipal.add(btnGerenciarUsuarios);
        painelPrincipal.add(btnGerenciarAreas);
        painelPrincipal.add(btnVerReservas);
        painelPrincipal.add(btnPublicarComunicado);
        // <<< MUDANÇA 4: Adicionar o novo botão ao painel
        painelPrincipal.add(btnRelatorios);

        add(painelPrincipal);
    }

    // Note que eu removi o construtor vazio "public PainelAdmin() {}" que não estava sendo usado,
    // para deixar o código mais limpo e evitar confusão.

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Para testar, use um ID de usuário fictício (ex: 1)
            PainelAdmin painel = new PainelAdmin(1);
            painel.setVisible(true);
        });
    }
}