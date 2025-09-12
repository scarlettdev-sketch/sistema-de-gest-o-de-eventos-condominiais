package View;
import javax.swing.*;

import DAO.UsuarioDAO;
import Model.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TelaLogin extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JLabel lblMensagem;

    public TelaLogin() {
        setTitle("Login - Condomínio");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(50, 40, 80, 25);
        painel.add(lblLogin);

        txtLogin = new JTextField(20);
        txtLogin.setBounds(120, 40, 200, 25);
        painel.add(txtLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 80, 80, 25);
        painel.add(lblSenha);

        txtSenha = new JPasswordField(20);
        txtSenha.setBounds(120, 80, 200, 25);
        painel.add(txtSenha);

        btnLogin = new JButton("Entrar");
        btnLogin.setBounds(160, 130, 80, 30);
        painel.add(btnLogin);

        lblMensagem = new JLabel("");
        lblMensagem.setBounds(50, 170, 300, 25);
        painel.add(lblMensagem);

        add(painel);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
    } // ← Esta chave estava faltando!

    private void realizarLogin() {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        if (login.isEmpty() || senha.isEmpty()) {
            lblMensagem.setText("Por favor, preencha todos os campos.");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.validarLogin(login, senha);

        if (usuario != null) {
            dispose(); // Fecha a tela de login


            int usuarioId = usuario.getId();


            String perfil = usuario.getPerfil();
            switch (perfil) {
                case "administrador":
                    // ALTERAÇÃO AQUI: Abre o painel do administrador
                    JOptionPane.showMessageDialog(null, "Olá, " + usuario.getNomeCompleto() + "! Bem-vindo ao painel administrativo.");
                    new PainelAdmin(usuarioId).setVisible(true);

                    PainelAdmin painelAdmin = new PainelAdmin();
                    painelAdmin.setVisible(true);

                    break;
                case "morador":
                    JOptionPane.showMessageDialog(null, "Olá, " + usuario.getNomeCompleto() + "! Bem-vindo.");
                    // Aqui iremos chamar a tela de morador
                    break;
                case "funcionario":
                    JOptionPane.showMessageDialog(null, "Olá, " + usuario.getNomeCompleto() + "! Bem-vindo.");
                    // Aqui iremos chamar a tela de funcionário
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Perfil não reconhecido.");
            }
        } else {
            lblMensagem.setText("Login ou senha inválidos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
