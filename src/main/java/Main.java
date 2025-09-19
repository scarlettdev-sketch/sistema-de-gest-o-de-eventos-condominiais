import Config.DatabaseInitializer;
import View.GUI.TelaLogin; // Usando o caminho com o novo subpacote GUI
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

public class Main {
    /**
     * Ponto de entrada principal da aplicação.
     */
    public static void main(String[] args) {

        // --- INÍCIO DO BLOCO DE CONFIGURAÇÃO DO TEMA ---
        // É importante que isso seja a PRIMEIRA coisa a ser executada,
        // antes de qualquer componente Swing ser criado.
        try {
            FlatLightLaf.setup(); // Configura o tema claro (Flat Light)
        } catch( Exception ex ) {
            System.err.println("Falha ao inicializar o tema (Look and Feel). Usando o padrão.");
        }
        // --- FIM DO BLOCO DE CONFIGURAÇÃO DO TEMA ---


        // Executa a criação da GUI na Event Dispatch Thread (EDT) do Swing.
        // Esta é a maneira correta e segura de iniciar uma aplicação Swing.
        SwingUtilities.invokeLater(() -> {
            System.out.println("🚀 Inicializando sistema...");
            DatabaseInitializer.initializeDatabase();

            System.out.println("🎯 Abrindo tela de login...");
            // Cria e exibe a tela de login
            new TelaLogin().setVisible(true);
        });
    }
}