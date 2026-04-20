package controllers;

import models.Faculte;
import models.FaculteDAO;
import javax.swing.*;
import java.util.List;

public class FaculteController {
    
    private FaculteDAO faculteDAO;
    
    public FaculteController() {
        this.faculteDAO = new FaculteDAO();
    }
    
    public boolean ajouterFaculte(String nomFac, String codeFac, String adresse, 
                                  String telephone, String email, String doyen) {
        // Validation
        if (nomFac == null || nomFac.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "❌ Le nom de la faculté est obligatoire !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (codeFac == null || codeFac.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "❌ Le code de la faculté est obligatoire !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validation email
        if (email != null && !email.trim().isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(null, 
                "❌ Format d'email invalide !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Faculte faculte = new Faculte(nomFac.trim(), codeFac.trim().toUpperCase(), 
                                      adresse.trim(), telephone.trim(), 
                                      email.trim(), doyen.trim());
        boolean success = faculteDAO.ajouter(faculte);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "✅ Faculté ajoutée avec succès !\n\n" +
                "Code: " + codeFac + "\n" +
                "Nom: " + nomFac, 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Erreur lors de l'ajout. Vérifiez que le code est unique.", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
    
    public List<Faculte> getAllFacultes() {
        return faculteDAO.getAll();
    }
    
    public boolean modifierFaculte(Faculte faculte) {
        if (faculte.getNomFac() == null || faculte.getNomFac().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "❌ Le nom de la faculté est obligatoire !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean success = faculteDAO.modifier(faculte);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "✅ Faculté modifiée avec succès !", 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Erreur lors de la modification.", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
    
    public boolean supprimerFaculte(int id, String codeFac, String nomFac) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Voulez-vous vraiment supprimer cette faculté ?\n\n" +
            "Code: " + codeFac + "\n" +
            "Nom: " + nomFac + "\n\n" +
            "⚠️ ATTENTION : Toutes les classes associées seront également supprimées !",
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = faculteDAO.supprimer(id);
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "✅ Faculté supprimée avec succès", 
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "❌ Erreur lors de la suppression.", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            return success;
        }
        return false;
    }
    
    public Faculte getFaculteById(int id) {
        return faculteDAO.getById(id);
    }
}