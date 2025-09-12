package DAO;

import Config.ConexaoBD;
import Model.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaDAO {

    public void cadastrarReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (usuario_id, area_comum_id, data, horario_inicio, horario_fim, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getUsuarioId());
            stmt.setInt(2, reserva.getAreaComumId());
            stmt.setDate(3, java.sql.Date.valueOf(reserva.getData()));
            stmt.setTime(4, java.sql.Time.valueOf(reserva.getHorarioInicio()));
            stmt.setTime(5, java.sql.Time.valueOf(reserva.getHorarioFim()));
            stmt.setString(6, reserva.getStatus());

            stmt.executeUpdate();
            System.out.println("Reserva cadastrada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao cadastrar reserva: " + e.getMessage());
        }
    }

    public void atualizarStatusReserva(int reservaId, String novoStatus) {
        String sql = "UPDATE reservas SET status = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, reservaId);
            stmt.executeUpdate();

            System.out.println("Status da reserva atualizado com sucesso para: " + novoStatus);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao atualizar status da reserva: " + e.getMessage());
        }
    }

    public List<Reserva> listarTodasReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas ORDER BY data DESC, horario_inicio DESC";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservas.add(criarReservaDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao listar todas as reservas: " + e.getMessage());
        }
        return reservas;
    }

    // Este é um método auxiliar para evitar duplicação de código
    private Reserva criarReservaDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int usuarioId = rs.getInt("usuario_id");
        int areaComumId = rs.getInt("area_comum_id");
        LocalDate data = rs.getDate("data").toLocalDate();
        LocalTime horarioInicio = rs.getTime("horario_inicio").toLocalTime();
        LocalTime horarioFim = rs.getTime("horario_fim").toLocalTime();
        String status = rs.getString("status");

        return new Reserva(id, usuarioId, areaComumId, data, horarioInicio, horarioFim, status);
    }
}
