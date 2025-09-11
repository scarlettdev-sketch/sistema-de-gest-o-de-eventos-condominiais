package View;

import DAO.UsuarioDAO;
import Model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GerenciarUsuariosGUI extends JFrame {

    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;
    private UsuarioDAO usuarioDAO;

    public GerenciarUsuariosGUI() {
        usuarioDAO = new UsuarioDAO();
        initComponents();
        carregarUsuarios();
    }

    private void initComponents() {
        setTitle("Gerenciamento de Usuários");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabela
        String[] colunas = {"ID", "Nome Completo", "Login", "Perfil", "Unidade", "CPF", "Email"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };
        tabelaUsuarios = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        tabelaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdicionar = new JButton("Adicionar Usuário");
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnExcluir = new JButton("Excluir Selecionado");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(new JLabel("Lista de Usuários Cadastrados", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> abrirFormularioUsuario(null));
        btnEditar.addActionListener(e -> editarUsuarioSelecionado());
        btnExcluir.addActionListener(e -> excluirUsuarioSelecionado());
        btnAtualizar.addActionListener(e -> carregarUsuarios());
    }

    private void carregarUsuarios() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        List<Usuario> usuarios = usuarioDAO.listarTodosUsuarios();
        for (Usuario u : usuarios) {
            modeloTabela.addRow(new Object[]{
                    u.getId(),
                    u.getNomeCompleto(),
                    u.getLogin(),
                    u.getPerfil(),
                    u.getUnidade(),
                    u.getCpf(),
                    u.getEmail()
            });
        }
    }

    private void abrirFormularioUsuario(Usuario usuario) {
        // --- Componentes do Formulário ---
        JTextField nomeField = new JTextField(usuario != null ? usuario.getNomeCompleto() : "");
        JTextField loginField = new JTextField(usuario != null ? usuario.getLogin() : "");
        JTextField cpfField = new JTextField(usuario != null ? usuario.getCpf() : "");
        JTextField unidadeField = new JTextField(usuario != null ? usuario.getUnidade() : "");
        JTextField emailField = new JTextField(usuario != null ? usuario.getEmail() : "");

        String[] perfis = {"morador", "administrador", "funcionario"};
        JComboBox<String> perfilComboBox = new JComboBox<>(perfis);
        if (usuario != null) {
            perfilComboBox.setSelectedItem(usuario.getPerfil());
        }

        JPasswordField senhaField = new JPasswordField();
        JPasswordField confirmaSenhaField = new JPasswordField();

        // --- Montagem do Painel ---
        JPanel painelForm = new JPanel(new GridLayout(0, 2, 10, 10));
        painelForm.add(new JLabel("Nome Completo:"));
        painelForm.add(nomeField);
        painelForm.add(new JLabel("Login:"));
        painelForm.add(loginField);
        painelForm.add(new JLabel("CPF:"));
        painelForm.add(cpfField);
        painelForm.add(new JLabel("Unidade/Apto:"));
        painelForm.add(unidadeField);
        painelForm.add(new JLabel("Email:"));
        painelForm.add(emailField);
        painelForm.add(new JLabel("Perfil:"));
        painelForm.add(perfilComboBox);

        if (usuario == null) { // Apenas pede senha para novos usuários
            painelForm.add(new JLabel("Senha:"));
            painelForm.add(senhaField);
            painelForm.add(new JLabel("Confirmar Senha:"));
            painelForm.add(confirmaSenhaField);
        }

        String titulo = (usuario == null) ? "Adicionar Novo Usuário" : "Editar Usuário";
        int result = JOptionPane.showConfirmDialog(this, painelForm, titulo,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // --- Lógica de Salvamento ---
            try {
                Usuario novoUsuario = new Usuario(
                        0, // ID é ignorado ou setado depois
                        nomeField.getText(),
                        (String) perfilComboBox.getSelectedItem(),
                        loginField.getText(),
                        cpfField.getText(),
                        unidadeField.getText(),
                        emailField.getText()
                );

                if (usuario == null) { // Modo Cadastro
                    String senha = new String(senhaField.getPassword());
                    String confirmaSenha = new String(confirmaSenhaField.getPassword());

                    if (senha.isEmpty() || !senha.equals(confirmaSenha)) {
                        JOptionPane.showMessageDialog(this, "As senhas não conferem ou estão vazias.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    usuarioDAO.cadastrarUsuario(novoUsuario, senha);
                    JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                } else { // Modo Edição
                    novoUsuario.setId(usuario.getId()); // Seta o ID correto para atualização
                    if (usuarioDAO.atualizarUsuario(novoUsuario)) {
                        JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Falha ao atualizar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
                carregarUsuarios(); // Atualiza a tabela
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarUsuarioSelecionado() {
        int linhaSelecionada = tabelaUsuarios.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int idUsuario = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            Usuario usuarioParaEditar = usuarioDAO.buscarUsuarioPorId(idUsuario);
            if (usuarioParaEditar != null) {
                abrirFormularioUsuario(usuarioParaEditar);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirUsuarioSelecionado() {
        int linhaSelecionada = tabelaUsuarios.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int idUsuario = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            String nomeUsuario = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o usuário '" + nomeUsuario + "'?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmacao == JOptionPane.YES_OPTION) {
                if (usuarioDAO.excluirUsuario(idUsuario)) {
                    JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!");
                    carregarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao excluir o usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}