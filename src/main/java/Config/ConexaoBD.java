package Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/condominio";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection getConexao() throws SQLException {
        try {
            // Registrar o driver MySQL explicitamente
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            return conexao;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver MySQL não encontrado.");
            throw new SQLException("Driver não encontrado", e);
        } catch (SQLException e) {
            System.err.println("Erro: Falha ao conectar ao banco de dados.");
            throw e;
        }
    }
}
