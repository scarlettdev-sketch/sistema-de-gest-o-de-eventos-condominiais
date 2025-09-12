package View;

import DAO.ComunicadoDAO;
import Model.Comunicado;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class PublicarComunicadoGUI extends JFrame {

    private JTextField txtTitulo;
    private JTextArea txtConteudo;
    private JButton btnPublicar;

    //Construtor que recebe o ID do usuário logado
    public PublicarComunicadoGUI(int usuarioId){
        setTitle("Publicar Comunicado");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Painel para o formulário
        JPanel painelForm = new JPanel(new GridLayout(2,1,5,5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Campo de Título
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.add(new JLabel("Título"), BorderLayout.NORTH);
        txtTitulo = new JTextField();
        painelTitulo.add(txtTitulo, BorderLayout.CENTER);

        //Área de Conteúdo
        JPanel painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.add(new JLabel("Conteúdo"), BorderLayout.NORTH);
        txtConteudo = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtConteudo);
        painelConteudo.add(scrollPane, BorderLayout.CENTER);

        painelForm.add(painelTitulo);
        painelForm.add(painelConteudo);

        //Botão de Publicar
        btnPublicar = new JButton("Publicar");
        JPanel painelBotao = new JPanel();
        painelBotao.add(btnPublicar);

        add(painelForm, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);

        // Adiciona a lógica ao botão
        btnPublicar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            String conteudo = txtConteudo.getText();

            if (titulo.isEmpty() || conteudo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título e conteúdo não podem ser vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cria o objeto Comunicado
            Comunicado novoComunicado = new Comunicado(titulo, conteudo, LocalDateTime.now(), usuarioId);

            // Chama o DAO para salvar no banco
            ComunicadoDAO comunicadoDAO = new ComunicadoDAO();
            comunicadoDAO.publicarComunicado(novoComunicado);

            JOptionPane.showMessageDialog(this, "Comunicado publicado com sucesso!");
            dispose(); // Fecha a tela após a publicação
        });
    }
}
