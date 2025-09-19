package Config;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "condominio";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public static void initializeDatabase() {
        try {
            // Registrar o driver MySQL explicitamente
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Conectar ao MySQL (sem especificar banco)
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            
            // Criar banco se não existir
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("✅ Banco de dados criado/verificado com sucesso!");
            
            // Conectar ao banco específico
            conn.close();
            conn = DriverManager.getConnection(DB_URL + DB_NAME, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            
            // Executar script SQL
            executeScriptFromFile(conn, "database/schema.sql");
            
            conn.close();
            System.out.println("✅ Banco de dados inicializado com sucesso!");
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Erro ao inicializar banco: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void executeScriptFromFile(Connection conn, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sql = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("--")) {
                    sql.append(line).append(" ");
                    
                    if (line.endsWith(";")) {
                        try (Statement stmt = conn.createStatement()) {
                            stmt.executeUpdate(sql.toString());
                            sql.setLength(0);
                        }
                    }
                }
            }
            
        } catch (IOException | SQLException e) {
            System.err.println("❌ Erro ao executar script: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        initializeDatabase();
    }
}