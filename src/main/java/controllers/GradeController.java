package controllers;

import models.Grade;
import models.GradeDAO;
import javax.swing.*;
import java.util.List;

public class GradeController {
    
    private GradeDAO gradeDAO;
    
    public GradeController() {
        this.gradeDAO = new GradeDAO();
    }
    
    public boolean ajouterGrade(String nomGrade, String niveau, String description) {
        if (nomGrade == null || nomGrade.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom du grade est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (niveau == null || niveau.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le niveau est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Grade grade = new Grade(nomGrade.trim(), niveau.trim(), description.trim());
        boolean success = gradeDAO.ajouter(grade);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "✅ Grade ajouté avec succès !\n\n" + nomGrade + " (" + niveau + ")", 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'ajout", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
    
    public List<Grade> getAllGrades() {
        return gradeDAO.getAll();
    }
    
    public boolean modifierGrade(Grade grade) {
        if (grade.getNomGrade() == null || grade.getNomGrade().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom du grade est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean success = gradeDAO.modifier(grade);
        
        if (success) {
            JOptionPane.showMessageDialog(null, "✅ Grade modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de la modification", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
    
    public boolean supprimerGrade(int id, String nomGrade) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Voulez-vous vraiment supprimer le grade : " + nomGrade + " ?\n" +
            "⚠️ Attention : Les enseignants associés perdront leur grade !",
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = gradeDAO.supprimer(id);
            if (success) {
                JOptionPane.showMessageDialog(null, "✅ Grade supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "❌ Erreur lors de la suppression", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            return success;
        }
        return false;
    }
    
    public Grade getGradeById(int id) {
        return gradeDAO.getById(id);
    }
}