package jerome;

import models.DatabaseConnection;
import views.MenuPrincipalView;
import javax.swing.*;

public class Jerome {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erreur Look and Feel: " + e.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> {
            System.out.println("🔍 Test de connexion à la base de données...");
            
            if (!DatabaseConnection.testConnection()) {
                int choix = JOptionPane.showConfirmDialog(null,
                    "⚠️ Impossible de se connecter à la base de données !\n\n" +
                    "Vérifiez que :\n" +
                    "• XAMPP est démarré (Apache + MySQL)\n" +
                    "• La base 'jerome_cirhulwire' existe\n\n" +
                    "Voulez-vous continuer sans base de données ?",
                    "Erreur de connexion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE);
                
                if (choix != JOptionPane.YES_OPTION) {
                    System.exit(1);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                    "✅ Connexion réussie !\n\n" +
                    "Bienvenue Jérôme Cirhulwire\n" +
                    "Système de Gestion Académique MVC",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            new MenuPrincipalView().setVisible(true);
        });
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConnection.closeConnection();
        }));
    }
}