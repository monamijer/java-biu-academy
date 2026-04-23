package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnsembleDAO {
    
    public boolean ajouter(Ensemble enseignant) {
        String query = "INSERT INTO ensemble (nom, prenom, nationalite, genre, grade_fk, date_naissance, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, enseignant.getNom());
            pstmt.setString(2, enseignant.getPrenom());
            pstmt.setString(3, enseignant.getNationalite());
            pstmt.setString(4, enseignant.getGenre());
            pstmt.setInt(5, enseignant.getGradeFk());
            pstmt.setDate(6, enseignant.getDateNaissance() != null ? new java.sql.Date(enseignant.getDateNaissance().getTime()) : null);
            pstmt.setString(7, enseignant.getTelephone());
            pstmt.setString(8, enseignant.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout enseignant: " + e.getMessage());
            return false;
        }
    }
    
    public List<Ensemble> getAll() {
        List<Ensemble> enseignants = new ArrayList<>();
        String query = "SELECT e.*, g.nom_grade FROM ensemble e LEFT JOIN grade g ON e.grade_fk = g.id_grade ORDER BY e.nom, e.prenom";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Ensemble e = new Ensemble();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setNationalite(rs.getString("nationalite"));
                e.setGenre(rs.getString("genre"));
                e.setGradeFk(rs.getInt("grade_fk"));
                e.setGradeNom(rs.getString("nom_grade"));
                e.setDateNaissance(rs.getDate("date_naissance"));
                e.setTelephone(rs.getString("telephone"));
                e.setEmail(rs.getString("email"));
                e.setDateCreation(rs.getTimestamp("date_creation"));
                enseignants.add(e);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture enseignants: " + e.getMessage());
        }
        return enseignants;
    }
    
    public Ensemble getById(int id) {
        String query = "SELECT e.*, g.nom_grade FROM ensemble e LEFT JOIN grade g ON e.grade_fk = g.id_grade WHERE e.id = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Ensemble e = new Ensemble();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setNationalite(rs.getString("nationalite"));
                e.setGenre(rs.getString("genre"));
                e.setGradeFk(rs.getInt("grade_fk"));
                e.setGradeNom(rs.getString("nom_grade"));
                e.setDateNaissance(rs.getDate("date_naissance"));
                e.setTelephone(rs.getString("telephone"));
                e.setEmail(rs.getString("email"));
                e.setDateCreation(rs.getTimestamp("date_creation"));
                return e;
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche enseignant: " + e.getMessage());
        }
        return null;
    }
    
    public boolean modifier(Ensemble enseignant) {
        String query = "UPDATE ensemble SET nom=?, prenom=?, nationalite=?, genre=?, grade_fk=?, date_naissance=?, telephone=?, email=? WHERE id=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, enseignant.getNom());
            pstmt.setString(2, enseignant.getPrenom());
            pstmt.setString(3, enseignant.getNationalite());
            pstmt.setString(4, enseignant.getGenre());
            pstmt.setInt(5, enseignant.getGradeFk());
            pstmt.setDate(6, enseignant.getDateNaissance() != null ? new java.sql.Date(enseignant.getDateNaissance().getTime()) : null);
            pstmt.setString(7, enseignant.getTelephone());
            pstmt.setString(8, enseignant.getEmail());
            pstmt.setInt(9, enseignant.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur modification enseignant: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) {
        String query = "DELETE FROM ensemble WHERE id = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression enseignant: " + e.getMessage());
            return false;
        }
    }
}