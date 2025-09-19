package DAO;

import Config.ConexaoBD;
import Model.Comunicado;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComunicadoDAO {

    public void publicarComunicado(Comunicado comunicado) {
        // CORREÇÃO: trocado "conteudo" por "mensagem"
        String sql = "INSERT INTO comunicados (titulo, mensagem, data_publicacao, usuario_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comunicado.getTitulo());
            stmt.setString(2, comunicado.getMensagem()); // CORREÇÃO: usar getMensagem()
            stmt.setTimestamp(3, Timestamp.valueOf(comunicado.getDataPublicacao()));
            stmt.setInt(4, comunicado.getUsuarioId());

            stmt.executeUpdate();
            System.out.println("Comunicado publicado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao publicar comunicado: " + e.getMessage());
        }
    }

    public List<Comunicado> listarTodosComunicados() {
        List<Comunicado> comunicados = new ArrayList<>();
        String sql = "SELECT * FROM comunicados ORDER BY data_publicacao DESC";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comunicados.add(criarComunicadoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao listar comunicados: " + e.getMessage());
        }
        return comunicados;
    }

    private Comunicado criarComunicadoDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        String mensagem = rs.getString("mensagem"); // CORREÇÃO: ler a coluna "mensagem"
        LocalDateTime dataPublicacao = rs.getTimestamp("data_publicacao").toLocalDateTime();
        int usuarioId = rs.getInt("usuario_id");

        // Assumindo que o construtor de Comunicado foi atualizado para aceitar 'mensagem'
        return new Comunicado(id, titulo, mensagem, dataPublicacao, usuarioId);
    }
}