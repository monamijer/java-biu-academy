package models;

import java.sql.*;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/jerome_cirhulwire?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection connection = null;
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL chargé avec succès");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Erreur de chargement du driver MySQL : " + e.getMessage());
        }
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion réussie à la base jerome_cirhulwire !");
            } catch (SQLException e) {
                System.err.println("❌ Erreur de connexion : " + e.getMessage());
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("🔒 Connexion fermée");
            } catch (SQLException e) {
                System.err.println("❌ Erreur fermeture : " + e.getMessage());
            }
        }
    }
    
    public static boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Test échoué : " + e.getMessage());
            return false;
        }
    }
}