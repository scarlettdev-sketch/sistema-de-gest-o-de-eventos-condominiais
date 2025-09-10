package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}