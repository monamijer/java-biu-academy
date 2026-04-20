package controllers;

import models.Cours;
import models.CoursDAO;
import javax.swing.*;
import java.util.List;

public class CoursController {
    
    private CoursDAO coursDAO;
    
    public CoursController() {
        this.coursDAO = new CoursDAO();
    }
    
    public boolean ajouterCours(String code, boolean divisible, String vhStr, 
                                String mardiProjetFkStr, String description) {
        // Validation
        if (code == null || code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le code est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        int vh, mardiProjetFk;
        try {
            vh = Integer.parseInt(vhStr);
            mardiProjetFk = Integer.parseInt(mardiProjetFkStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ VH et Mardi Projet FK doivent être des nombres !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Cours cours = new Cours(code.trim(), divisible, vh, mardiProjetFk, description.trim());
        boolean success = coursDAO.ajouter(cours);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "✅ Cours ajouté avec succès !\n\nCode: " + code, 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'ajout", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
    
    public List<Cours> getAllCours() {
        return coursDAO.getAll();
    }
    
    public boolean supprimerCours(int id, String code) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Voulez-vous vraiment supprimer le cours : " + code + " ?",
            "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            return coursDAO.supprimer(id);
        }
        return false;
    }
}