package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FaculteDAO {
    
    public boolean ajouter(Faculte faculte) {
        String query = "INSERT INTO faculte (nom_fac, code_fac, adresse, telephone, email, doyen) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, faculte.getNomFac());
            pstmt.setString(2, faculte.getCodeFac());
            pstmt.setString(3, faculte.getAdresse());
            pstmt.setString(4, faculte.getTelephone());
            pstmt.setString(5, faculte.getEmail());
            pstmt.setString(6, faculte.getDoyen());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout faculte: " + e.getMessage());
            return false;
        }
    }
    
    public List<Faculte> getAll() {
        List<Faculte> facultes = new ArrayList<>();
        String query = "SELECT * FROM faculte ORDER BY nom_fac";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Faculte faculte = new Faculte();
                faculte.setIdFac(rs.getInt("id_fac"));
                faculte.setNomFac(rs.getString("nom_fac"));
                faculte.setCodeFac(rs.getString("code_fac"));
                faculte.setAdresse(rs.getString("adresse"));
                faculte.setTelephone(rs.getString("telephone"));
                faculte.setEmail(rs.getString("email"));
                faculte.setDoyen(rs.getString("doyen"));
                faculte.setDateCreation(rs.getTimestamp("date_creation"));
                facultes.add(faculte);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture facultes: " + e.getMessage());
        }
        return facultes;
    }
    
    public Faculte getById(int id) {
        String query = "SELECT * FROM faculte WHERE id_fac = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Faculte faculte = new Faculte();
                faculte.setIdFac(rs.getInt("id_fac"));
                faculte.setNomFac(rs.getString("nom_fac"));
                faculte.setCodeFac(rs.getString("code_fac"));
                faculte.setAdresse(rs.getString("adresse"));
                faculte.setTelephone(rs.getString("telephone"));
                faculte.setEmail(rs.getString("email"));
                faculte.setDoyen(rs.getString("doyen"));
                faculte.setDateCreation(rs.getTimestamp("date_creation"));
                return faculte;
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche faculte: " + e.getMessage());
        }
        return null;
    }
    
    public boolean modifier(Faculte faculte) {
        String query = "UPDATE faculte SET nom_fac=?, code_fac=?, adresse=?, telephone=?, email=?, doyen=? WHERE id_fac=?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, faculte.getNomFac());
            pstmt.setString(2, faculte.getCodeFac());
            pstmt.setString(3, faculte.getAdresse());
            pstmt.setString(4, faculte.getTelephone());
            pstmt.setString(5, faculte.getEmail());
            pstmt.setString(6, faculte.getDoyen());
            pstmt.setInt(7, faculte.getIdFac());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur modification faculte: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) {
        String query = "DELETE FROM faculte WHERE id_fac = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression faculte: " + e.getMessage());
            return false;
        }
    }
}