package View;

import javax.swing.*;
import java.awt.*;

public class GerenciarUsuariosGUI extends JFrame {

    public GerenciarUsuariosGUI() {
        setTitle("Gerenciar Usuários");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE_ON_CLOSE fecha apenas a janela atual
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Tela de Gerenciamento de Usuários", SwingConstants.CENTER);
        add(titulo, BorderLayout.CENTER);
    }
}
