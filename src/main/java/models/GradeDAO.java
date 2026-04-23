package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    
    public boolean ajouter(Grade grade) {
        String query = "INSERT INTO grade (nom_grade, niveau, description) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, grade.getNomGrade());
            pstmt.setString(2, grade.getNiveau());
            pstmt.setString(3, grade.getDescription());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout grade: " + e.getMessage());
            return false;
        }
    }
    
    public List<Grade> getAll() {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT * FROM grade ORDER BY niveau";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Grade g = new Grade();
                g.setIdGrade(rs.getInt("id_grade"));
                g.setNomGrade(rs.getString("nom_grade"));
                g.setNiveau(rs.getString("niveau"));
                g.setDescription(rs.getString("description"));
                g.setDateCreation(rs.getTimestamp("date_creation"));
                grades.add(g);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture grades: " + e.getMessage());
        }
        return grades;
    }
    
    public Grade getById(int id) {
        String query = "SELECT * FROM grade WHERE id_grade = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Grade g = new Grade();
                g.setIdGrade(rs.getInt("id_grade"));
                g.setNomGrade(rs.getString("nom_grade"));
                g.setNiveau(rs.getString("niveau"));
                g.setDescription(rs.getString("description"));
                g.setDateCreation(rs.getTimestamp("date_creation"));
                return g;
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche grade: " + e.getMessage());
        }
        return null;
    }
    
    public boolean modifier(Grade grade) {
        String query = "UPDATE grade SET nom_grade=?, niveau=?, description=? WHERE id_grade=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, grade.getNomGrade());
            pstmt.setString(2, grade.getNiveau());
            pstmt.setString(3, grade.getDescription());
            pstmt.setInt(4, grade.getIdGrade());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur modification grade: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) {
        // Mettre à NULL les enseignants qui ont ce grade
        String updateQuery = "UPDATE ensemble SET grade_fk = NULL WHERE grade_fk = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(updateQuery)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour enseignants: " + e.getMessage());
        }
        
        // Supprimer le grade
        String query = "DELETE FROM grade WHERE id_grade = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression grade: " + e.getMessage());
            return false;
        }
    }
}