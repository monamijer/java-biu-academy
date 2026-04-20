package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnsembleDAO {
    
    public boolean ajouter(Ensemble ensemble) {
        String query = "INSERT INTO ensemble (nom, prenom, nationalite, genre, grade_fk, date_naissance, telephone, email) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, ensemble.getNom());
            pstmt.setString(2, ensemble.getPrenom());
            pstmt.setString(3, ensemble.getNationalite());
            pstmt.setString(4, ensemble.getGenre());
            pstmt.setInt(5, ensemble.getGradeFk());
            pstmt.setDate(6, ensemble.getDateNaissance() != null ? 
                         new java.sql.Date(ensemble.getDateNaissance().getTime()) : null);
            pstmt.setString(7, ensemble.getTelephone());
            pstmt.setString(8, ensemble.getEmail());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout ensemble: " + e.getMessage());
            return false;
        }
    }
    
    public List<Ensemble> getAll() {
        List<Ensemble> ensembles = new ArrayList<>();
        String query = "SELECT e.*, g.nom_grade FROM ensemble e " +
                      "LEFT JOIN grade g ON e.grade_fk = g.id_grade " +
                      "ORDER BY e.nom, e.prenom";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Ensemble ensemble = new Ensemble();
                ensemble.setId(rs.getInt("id"));
                ensemble.setNom(rs.getString("nom"));
                ensemble.setPrenom(rs.getString("prenom"));
                ensemble.setNationalite(rs.getString("nationalite"));
                ensemble.setGenre(rs.getString("genre"));
                ensemble.setGradeFk(rs.getInt("grade_fk"));
                ensemble.setDateNaissance(rs.getDate("date_naissance"));
                ensemble.setTelephone(rs.getString("telephone"));
                ensemble.setEmail(rs.getString("email"));
                ensemble.setDateCreation(rs.getTimestamp("date_creation"));
                ensembles.add(ensemble);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture ensembles: " + e.getMessage());
        }
        return ensembles;
    }
    
    public Ensemble getById(int id) {
        String query = "SELECT * FROM ensemble WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Ensemble ensemble = new Ensemble();
                ensemble.setId(rs.getInt("id"));
                ensemble.setNom(rs.getString("nom"));
                ensemble.setPrenom(rs.getString("prenom"));
                ensemble.setNationalite(rs.getString("nationalite"));
                ensemble.setGenre(rs.getString("genre"));
                ensemble.setGradeFk(rs.getInt("grade_fk"));
                ensemble.setDateNaissance(rs.getDate("date_naissance"));
                ensemble.setTelephone(rs.getString("telephone"));
                ensemble.setEmail(rs.getString("email"));
                ensemble.setDateCreation(rs.getTimestamp("date_creation"));
                return ensemble;
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche ensemble: " + e.getMessage());
        }
        return null;
    }
    
    public boolean modifier(Ensemble ensemble) {
        String query = "UPDATE ensemble SET nom=?, prenom=?, nationalite=?, genre=?, " +
                      "grade_fk=?, date_naissance=?, telephone=?, email=? WHERE id=?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, ensemble.getNom());
            pstmt.setString(2, ensemble.getPrenom());
            pstmt.setString(3, ensemble.getNationalite());
            pstmt.setString(4, ensemble.getGenre());
            pstmt.setInt(5, ensemble.getGradeFk());
            pstmt.setDate(6, ensemble.getDateNaissance() != null ? 
                         new java.sql.Date(ensemble.getDateNaissance().getTime()) : null);
            pstmt.setString(7, ensemble.getTelephone());
            pstmt.setString(8, ensemble.getEmail());
            pstmt.setInt(9, ensemble.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur modification ensemble: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) {
        String query = "DELETE FROM ensemble WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression ensemble: " + e.getMessage());
            return false;
        }
    }
}