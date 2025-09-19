package View;

import javax.swing.*;
import java.awt.*;
import View.GUI.FazerReservaGUI;
import View.GUI.MinhasReservasGUI;
import View.GUI.VerComunicadosGUI;

public class PainelMorador extends JFrame {

    private int usuarioId;

    public PainelMorador(int usuarioId){
        this.usuarioId = usuarioId;

        setTitle("Painel do Morador");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(3, 1, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Botões
        JButton btnVerReservas = new JButton("Minhas Reservas");
        JButton btnFazerReserva = new JButton("Fazer Nova Reserva");
        JButton btnVerComunicados = new JButton("Ver Comunicados");

        // Lógica para o botão "Fazer Nova Reserva"
        btnFazerReserva.addActionListener(e -> {
            FazerReservaGUI telaReserva = new FazerReservaGUI(this.usuarioId);
            telaReserva.setVisible(true);
        });

        btnFazerReserva.addActionListener(e -> {
        });

        btnVerComunicados.addActionListener(e -> {
        });

        painelPrincipal.add(btnVerReservas);
        painelPrincipal.add(btnFazerReserva);
        painelPrincipal.add(btnVerComunicados);

        add(painelPrincipal);

        // Lógica para o botão "Minhas Reservas"
        btnVerReservas.addActionListener(e -> {
            MinhasReservasGUI telaMinhasReservas = new MinhasReservasGUI(this.usuarioId);
            telaMinhasReservas.setVisible(true);
        });

        // Lógica para o botão "Ver Comunicados"
        btnVerComunicados.addActionListener(e -> {
            VerComunicadosGUI telaComunicados = new VerComunicadosGUI();
            telaComunicados.setVisible(true);
        });
    }
}
