package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Config.ConexaoBD;
import Model.AreaComum;

public class AreaComumDAO {

    public List<AreaComum> listarAreasAtivas() {
        List<AreaComum> areas = new ArrayList<>();
        String sql = "SELECT * FROM areas_comuns WHERE ativa = true ORDER BY nome";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AreaComum area = new AreaComum(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("capacidade_maxima"),
                        rs.getString("regras_uso"),
                        rs.getDouble("taxa_reserva"),
                        rs.getBoolean("ativa"),
                        rs.getString("descricao")
                );
                areas.add(area);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar áreas: " + e.getMessage());
        }
        return areas;
    }

    public boolean cadastrarArea(AreaComum area) {
        String sql = "INSERT INTO areas_comuns (nome, capacidade_maxima, regras_uso, taxa_reserva, descricao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, area.getNome());
            stmt.setInt(2, area.getCapacidadeMaxima());
            stmt.setString(3, area.getRegrasUso());
            stmt.setDouble(4, area.getTaxaReserva());
            stmt.setString(5, area.getDescricao());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar área: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarArea(AreaComum area) {
        String sql = "UPDATE areas_comuns SET nome=?, capacidade_maxima=?, regras_uso=?, taxa_reserva=?, descricao=? WHERE id=?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, area.getNome());
            stmt.setInt(2, area.getCapacidadeMaxima());
            stmt.setString(3, area.getRegrasUso());
            stmt.setDouble(4, area.getTaxaReserva());
            stmt.setString(5, area.getDescricao());
            stmt.setInt(6, area.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar área: " + e.getMessage());
            return false;
        }
    }

    public boolean desativarArea(int id) {
        String sql = "UPDATE areas_comuns SET ativa = false WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao desativar área: " + e.getMessage());
            return false;
        }
    }

    public AreaComum buscarAreaPorId(int id) {
        String sql = "SELECT * FROM areas_comuns WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new AreaComum(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("capacidade_maxima"),
                        rs.getString("regras_uso"),
                        rs.getDouble("taxa_reserva"),
                        rs.getBoolean("ativa"),
                        rs.getString("descricao")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar área por ID: " + e.getMessage());
        }
        return null;
    }
}