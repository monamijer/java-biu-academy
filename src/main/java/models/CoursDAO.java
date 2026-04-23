package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDAO {

    public boolean ajouter(Cours cours) {
        String query = "INSERT INTO cours (code, intitule, vh, professeur_id, faculte_id, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, cours.getCode());
            pstmt.setString(2, cours.getIntitule());
            pstmt.setInt(3, cours.getVh());
            pstmt.setInt(4, cours.getProfesseurId());
            pstmt.setInt(5, cours.getFaculteId());
            pstmt.setString(6, cours.getDescription());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            System.err.println("Erreur ajout cours: " + e.getMessage()); 
            return false; 
        }
    }

    public List<Cours> getAll() {
        List<Cours> list = new ArrayList<>();
        String query = "SELECT c.*, " +
                      "CONCAT(e.prenom, ' ', e.nom) AS professeur_nom, " +
                      "f.nom_fac AS faculte_nom " +
                      "FROM cours c " +
                      "LEFT JOIN ensemble e ON c.professeur_id = e.id " +
                      "LEFT JOIN faculte f ON c.faculte_id = f.id_fac " +
                      "ORDER BY c.code";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Cours c = new Cours();
                c.setId(rs.getInt("id"));
                c.setCode(rs.getString("code"));
                c.setIntitule(rs.getString("intitule"));
                c.setVh(rs.getInt("vh"));
                c.setProfesseurId(rs.getInt("professeur_id"));
                c.setFaculteId(rs.getInt("faculte_id"));
                c.setProfesseurNom(rs.getString("professeur_nom"));
                c.setFaculteNom(rs.getString("faculte_nom"));
                c.setDescription(rs.getString("description"));
                c.setDateCreation(rs.getTimestamp("date_creation"));
                list.add(c);
            }
        } catch (SQLException e) { 
            System.err.println("Erreur lecture cours: " + e.getMessage()); 
        }
        return list;
    }

    public Cours getById(int id) {
        String query = "SELECT c.*, " +
                      "CONCAT(e.prenom, ' ', e.nom) AS professeur_nom, " +
                      "f.nom_fac AS faculte_nom " +
                      "FROM cours c " +
                      "LEFT JOIN ensemble e ON c.professeur_id = e.id " +
                      "LEFT JOIN faculte f ON c.faculte_id = f.id_fac " +
                      "WHERE c.id = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Cours c = new Cours();
                c.setId(rs.getInt("id"));
                c.setCode(rs.getString("code"));
                c.setIntitule(rs.getString("intitule"));
                c.setVh(rs.getInt("vh"));
                c.setProfesseurId(rs.getInt("professeur_id"));
                c.setFaculteId(rs.getInt("faculte_id"));
                c.setProfesseurNom(rs.getString("professeur_nom"));
                c.setFaculteNom(rs.getString("faculte_nom"));
                c.setDescription(rs.getString("description"));
                c.setDateCreation(rs.getTimestamp("date_creation"));
                return c;
            }
        } catch (SQLException e) { 
            System.err.println("Erreur getById cours: " + e.getMessage()); 
        }
        return null;
    }

    public boolean modifier(Cours cours) {
        String query = "UPDATE cours SET code=?, intitule=?, vh=?, professeur_id=?, faculte_id=?, description=? WHERE id=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, cours.getCode());
            pstmt.setString(2, cours.getIntitule());
            pstmt.setInt(3, cours.getVh());
            pstmt.setInt(4, cours.getProfesseurId());
            pstmt.setInt(5, cours.getFaculteId());
            pstmt.setString(6, cours.getDescription());
            pstmt.setInt(7, cours.getId());
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