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

    /**
     * Salva uma nova reserva no banco de dados.
     * @param reserva O objeto Reserva a ser salvo.
     * @return true se a operação for bem-sucedida, false caso contrário.
     */
    public boolean salvarReserva(Reserva reserva) {
        // CORRIGIDO: Query com a coluna 'data_reserva' e ordem correta dos campos
        String sql = "INSERT INTO reservas (usuario_id, area_comum_id, data_reserva, horario_inicio, horario_fim, status, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // CORRIGIDO: Ordem dos parâmetros correspondendo à query SQL
            stmt.setInt(1, reserva.getUsuarioId());
            stmt.setInt(2, reserva.getAreaComumId());
            stmt.setDate(3, java.sql.Date.valueOf(reserva.getData()));
            stmt.setTime(4, java.sql.Time.valueOf(reserva.getHorarioInicio()));
            stmt.setTime(5, java.sql.Time.valueOf(reserva.getHorarioFim()));
            stmt.setString(6, reserva.getStatus());
            stmt.setString(7, reserva.getDescricao());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar reserva: " + e.getMessage());
            e.printStackTrace(); // Ajuda a depurar
            return false;
        }
    }

    /**
     * Atualiza o status de uma reserva existente (ex: para 'aprovada' ou 'rejeitada').
     * @param reservaId O ID da reserva a ser atualizada.
     * @param novoStatus O novo status da reserva.
     */
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

    /**
     * Lista todas as reservas do banco de dados.
     * @return Uma lista de objetos Reserva.
     */
    public List<Reserva> listarTodasReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas ORDER BY data_reserva DESC, horario_inicio DESC";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
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

    /**
     * Lista todas as reservas de um usuário específico.
     * @param usuarioId O ID do usuário.
     * @return Uma lista de objetos Reserva do usuário especificado.
     */
    public List<Reserva> listarReservasPorUsuarioId(int usuarioId){
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE usuario_id = ? ORDER BY data_reserva DESC, horario_inicio DESC";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    reservas.add(criarReservaDoResultSet(rs));
                }
            }
        } catch (SQLException e){
            System.err.println("Erro ao listar reservas do usuário:" + e.getMessage());
            e.printStackTrace();
        }
        return reservas;
    }

    /**
     * Verifica se já existe uma reserva conflitante para uma determinada área, data e intervalo de tempo.
     * A lógica de sobreposição é: (InicioA < FimB) E (InicioB < FimA).
     *
     * @param areaComumId   O ID da área a ser verificada.
     * @param data          A data da nova reserva.
     * @param horarioInicio O horário de início da nova reserva.
     * @param horarioFim    O horário de término da nova reserva.
     * @return true se houver conflito, false caso contrário.
     */
    public boolean existeConflitoDeHorario(int areaComumId, LocalDate data, LocalTime horarioInicio, LocalTime horarioFim) {
        String sql = "SELECT COUNT(*) FROM reservas " +
                "WHERE area_comum_id = ? " +
                "AND data_reserva = ? " +
                "AND status IN ('aprovada', 'pendente') " +
                "AND horario_inicio < ? " +  // Início da reserva existente < Fim da nova reserva
                "AND horario_fim > ?";      // Fim da reserva existente > Início da nova reserva

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, areaComumId);
            stmt.setDate(2, java.sql.Date.valueOf(data));
            stmt.setTime(3, java.sql.Time.valueOf(horarioFim));
            stmt.setTime(4, java.sql.Time.valueOf(horarioInicio));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar conflito de horário: " + e.getMessage());
            e.printStackTrace();
            return true; // É mais seguro assumir que há conflito em caso de erro.
        }
        return false;
    }

    /**
     * Método auxiliar para criar um objeto Reserva a partir de um ResultSet.
     * @param rs O ResultSet contendo os dados da reserva.
     * @return Um objeto Reserva preenchido.
     * @throws SQLException
     */
    private Reserva criarReservaDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int usuarioId = rs.getInt("usuario_id");
        int areaComumId = rs.getInt("area_comum_id");
        LocalDate data = rs.getDate("data_reserva").toLocalDate();
        LocalTime horarioInicio = rs.getTime("horario_inicio").toLocalTime();
        LocalTime horarioFim = rs.getTime("horario_fim").toLocalTime();
        String status = rs.getString("status");
        String descricao = rs.getString("descricao");

        return new Reserva(id, usuarioId, areaComumId, data, horarioInicio, horarioFim, status, descricao);
    }

    /**
     * Rejeita uma reserva, atualizando seu status e salvando o motivo da rejeição.
     * @param reservaId O ID da reserva a ser rejeitada.
     * @param motivo O texto com a justificativa da rejeição.
     * @return true se a operação for bem-sucedida, false caso contrário.
     */
    public boolean rejeitarReserva(int reservaId, String motivo) {
        String sql = "UPDATE reservas SET status = 'rejeitada', motivo_rejeicao = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao(); // Corrigido para ConexaoBD
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, motivo);
            stmt.setInt(2, reservaId);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao rejeitar reserva: " + e.getMessage());
            return false;
        }
    }
}