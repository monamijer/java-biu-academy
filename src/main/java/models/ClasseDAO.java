package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    
    public boolean ajouter(Classe classe) {
        String query = "INSERT INTO classe (nom_classe, id_fac, responsable, nom_projet, type_classe, description) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, classe.getNomClasse());
            pstmt.setInt(2, classe.getIdFac());
            pstmt.setString(3, classe.getResponsable());
            pstmt.setString(4, classe.getNomProjet());
            pstmt.setString(5, classe.getTypeClasse());
            pstmt.setString(6, classe.getDescription());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout classe: " + e.getMessage());
            return false;
        }
    }
    
    public List<Classe> getAll() {
        List<Classe> classes = new ArrayList<>();
        String query = "SELECT * FROM classe ORDER BY nom_classe";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Classe classe = new Classe();
                classe.setIdClasse(rs.getInt("id_classe"));
                classe.setNomClasse(rs.getString("nom_classe"));
                classe.setIdFac(rs.getInt("id_fac"));
                classe.setResponsable(rs.getString("responsable"));
                classe.setNomProjet(rs.getString("nom_projet"));
                classe.setTypeClasse(rs.getString("type_classe"));
                classe.setDescription(rs.getString("description"));
                classe.setDateCreation(rs.getTimestamp("date_creation"));
                classes.add(classe);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture classes: " + e.getMessage());
        }
        return classes;
    }
    
    public boolean modifier(Classe classe) {
        String query = "UPDATE classe SET nom_classe=?, id_fac=?, responsable=?, nom_projet=?, type_classe=?, description=? WHERE id_classe=?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, classe.getNomClasse());
            pstmt.setInt(2, classe.getIdFac());
            pstmt.setString(3, classe.getResponsable());
            pstmt.setString(4, classe.getNomProjet());
            pstmt.setString(5, classe.getTypeClasse());
            pstmt.setString(6, classe.getDescription());
            pstmt.setInt(7, classe.getIdClasse());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur modification classe: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) {
        String query = "DELETE FROM classe WHERE id_classe = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression classe: " + e.getMessage());
            return false;
        }
    }
}