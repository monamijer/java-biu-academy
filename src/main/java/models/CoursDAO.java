package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDAO {
    
    public boolean ajouter(Cours cours) {
        String query = "INSERT INTO cours (code, divisible, vh, mardi_projet_fk, description) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, cours.getCode());
            pstmt.setBoolean(2, cours.isDivisible());
            pstmt.setInt(3, cours.getVh());
            pstmt.setInt(4, cours.getMardiProjetFk());
            pstmt.setString(5, cours.getDescription());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout cours: " + e.getMessage());
            return false;
        }
    }
    
    public List<Cours> getAll() {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT * FROM cours ORDER BY code";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setCode(rs.getString("code"));
                cours.setDivisible(rs.getBoolean("divisible"));
                cours.setVh(rs.getInt("vh"));
                cours.setMardiProjetFk(rs.getInt("mardi_projet_fk"));
                cours.setDescription(rs.getString("description"));
                cours.setDateCreation(rs.getTimestamp("date_creation"));
                coursList.add(cours);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture cours: " + e.getMessage());
        }
        return coursList;
    }
    
    public boolean modifier(Cours cours) {
        String query = "UPDATE cours SET code=?, divisible=?, vh=?, mardi_projet_fk=?, description=? WHERE id=?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, cours.getCode());
            pstmt.setBoolean(2, cours.isDivisible());
            pstmt.setInt(3, cours.getVh());
            pstmt.setInt(4, cours.getMardiProjetFk());
            pstmt.setString(5, cours.getDescription());
            pstmt.setInt(6, cours.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur modification cours: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) {
        String query = "DELETE FROM cours WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression cours: " + e.getMessage());
            return false;
        }
    }
}