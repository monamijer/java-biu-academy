package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    
    // CRUD - Create
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
    
    // CRUD - Read All
    public List<Grade> getAll() {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT * FROM grade ORDER BY niveau, nom_grade";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setIdGrade(rs.getInt("id_grade"));
                grade.setNomGrade(rs.getString("nom_grade"));
                grade.setNiveau(rs.getString("niveau"));
                grade.setDescription(rs.getString("description"));
                grade.setDateCreation(rs.getTimestamp("date_creation"));
                grades.add(grade);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture grades: " + e.getMessage());
        }
        return grades;
    }
    
    // CRUD - Read One
    public Grade getById(int id) {
        String query = "SELECT * FROM grade WHERE id_grade = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Grade grade = new Grade();
                grade.setIdGrade(rs.getInt("id_grade"));
                grade.setNomGrade(rs.getString("nom_grade"));
                grade.setNiveau(rs.getString("niveau"));
                grade.setDescription(rs.getString("description"));
                grade.setDateCreation(rs.getTimestamp("date_creation"));
                return grade;
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche grade: " + e.getMessage());
        }
        return null;
    }
    
    // CRUD - Update
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
    
    // CRUD - Delete
    public boolean supprimer(int id) {
        String query = "DELETE FROM grade WHERE id_grade = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression grade: " + e.getMessage());
            return false;
        }
    }
    
    // Méthode utilitaire pour remplir les ComboBox
    public List<Grade> getGradesForCombo() {
        return getAll();
    }
}