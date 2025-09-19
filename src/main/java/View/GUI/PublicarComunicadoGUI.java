package View.GUI;

import DAO.ComunicadoDAO;
import Model.Comunicado;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class PublicarComunicadoGUI extends JFrame {

    private JTextField txtTitulo;
    private JTextArea txtMensagem; // RENOMEADO: de txtConteudo para txtMensagem
    private JButton btnPublicar;

    // Construtor que recebe o ID do usuário logado
    public PublicarComunicadoGUI(int usuarioId) {
        setTitle("Publicar Novo Comunicado");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Painel para o formulário
        JPanel painelForm = new JPanel(new GridLayout(2, 1, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo de Título
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.add(new JLabel("Título:"), BorderLayout.NORTH);
        txtTitulo = new JTextField();
        painelTitulo.add(txtTitulo, BorderLayout.CENTER);

        // Área de Mensagem
        JPanel painelMensagem = new JPanel(new BorderLayout()); // RENOMEADO
        painelMensagem.add(new JLabel("Mensagem:"), BorderLayout.NORTH); // CORRIGIDO
        txtMensagem = new JTextArea(); // CORRIGIDO
        JScrollPane scrollPane = new JScrollPane(txtMensagem); // CORRIGIDO
        painelMensagem.add(scrollPane, BorderLayout.CENTER); // CORRIGIDO

        painelForm.add(painelTitulo);
        painelForm.add(painelMensagem); // CORRIGIDO

        // Botão de Publicar
        btnPublicar = new JButton("Publicar");
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Layout melhorado
        painelBotao.add(btnPublicar);

        add(painelForm, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);

        // Adiciona a lógica ao botão
        btnPublicar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String mensagem = txtMensagem.getText().trim(); // CORRIGIDO

            if (titulo.isEmpty() || mensagem.isEmpty()) { // CORRIGIDO
                JOptionPane.showMessageDialog(this, "Título e mensagem não podem ser vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cria o objeto Comunicado usando a nova variável 'mensagem'
            // (Certifique-se que o construtor em Model/Comunicado.java também foi atualizado)
            Comunicado novoComunicado = new Comunicado(titulo, mensagem, LocalDateTime.now(), usuarioId);

            // Chama o DAO para salvar no banco
            ComunicadoDAO comunicadoDAO = new ComunicadoDAO();
            comunicadoDAO.publicarComunicado(novoComunicado);

            JOptionPane.showMessageDialog(this, "Comunicado publicado com sucesso!");
            dispose(); // Fecha a tela após a publicação
        });
    }
}