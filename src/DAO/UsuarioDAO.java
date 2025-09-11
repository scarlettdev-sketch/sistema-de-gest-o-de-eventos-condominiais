package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Config.ConexaoBD;
import Model.Usuario;

public class UsuarioDAO {

    public Usuario validarLogin(String login, String senha) {
        String sql = "SELECT id, nome_completo, senha_hash, perfil FROM usuarios WHERE login = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaDoBanco = rs.getString("senha_hash");

                if (senha.equals(senhaDoBanco)) {
                    int id = rs.getInt("id");
                    String nomeCompleto = rs.getString("nome_completo");
                    String perfil = rs.getString("perfil");
                    return new Usuario(id, nomeCompleto, perfil);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
        }
        return null;
    }

    public void cadastrarUsuario(Usuario usuario, String senha) {
        String sql = "INSERT INTO usuarios (nome_completo, cpf, email, unidade, login, senha_hash, perfil) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String senhaEmTextoPuro = senha;

            stmt.setString(1, usuario.getNomeCompleto());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, "nao_informado@email.com");
            stmt.setString(4, "000");
            stmt.setString(5, usuario.getLogin());
            stmt.setString(6, senhaEmTextoPuro);
            stmt.setString(7, usuario.getPerfil());

            stmt.executeUpdate();
            System.out.println("Usuário " + usuario.getNomeCompleto() + " cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> listarTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        // Evitamos selecionar a senha_hash por segurança
        String sql = "SELECT id, nome_completo, perfil, login, cpf, unidade, email FROM usuarios ORDER BY nome_completo";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome_completo"),
                        rs.getString("perfil"),
                        rs.getString("login"),
                        rs.getString("cpf"),
                        rs.getString("unidade"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    /**
     * Busca um usuário específico pelo seu ID.
     * @param id O ID do usuário a ser buscado.
     * @return Um objeto Usuario, ou null se não for encontrado.
     */
    public Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT id, nome_completo, perfil, login, cpf, unidade, email FROM usuarios WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome_completo"),
                        rs.getString("perfil"),
                        rs.getString("login"),
                        rs.getString("cpf"),
                        rs.getString("unidade"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza os dados de um usuário existente no banco.
     * Não altera a senha.
     * @param usuario O objeto Usuario com os dados atualizados.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    public boolean atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome_completo = ?, cpf = ?, email = ?, unidade = ?, login = ?, perfil = ? WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNomeCompleto());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getUnidade());
            stmt.setString(5, usuario.getLogin());
            stmt.setString(6, usuario.getPerfil());
            stmt.setInt(7, usuario.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exclui um usuário do banco de dados pelo seu ID.
     * @param id O ID do usuário a ser excluído.
     * @return true se a exclusão foi bem-sucedida, false caso contrário.
     */
    public boolean excluirUsuario(int id) {
        // Cuidado: Em um sistema real, talvez seja melhor desativar o usuário (soft delete)
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        }
    }
}