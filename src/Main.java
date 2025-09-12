import Config.DatabaseInitializer;
import View.TelaLogin;

public class Main {
    public static void main(String[] args) {
        // Inicializar banco de dados na primeira execução
        System.out.println("🚀 Inicializando sistema...");
        DatabaseInitializer.initializeDatabase();
        
        // Iniciar aplicação
        System.out.println("🎯 Abrindo tela de login...");
        new TelaLogin().setVisible(true);
    }
}