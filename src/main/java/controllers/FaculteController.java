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
    
    public boolean ajouterFaculte(String nom, String code, String adresse, String tel, String email, String doyen) {
        if (nom == null || nom.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom de la faculté est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (code == null || code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le code de la faculté est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Faculte faculte = new Faculte(nom.trim(), code.trim().toUpperCase(), adresse, tel, email, doyen);
        boolean ok = faculteDAO.ajouter(faculte);
        if (ok) 
            JOptionPane.showMessageDialog(null, "✅ Faculté ajoutée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }
    
    public boolean modifierFaculte(Faculte faculte) {
        boolean ok = faculteDAO.modifier(faculte);
        if (ok) 
            JOptionPane.showMessageDialog(null, "✅ Faculté modifiée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }
    
    public boolean supprimerFaculte(int id, String code, String nom) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Voulez-vous vraiment supprimer la faculté : " + nom + " (" + code + ") ?\n⚠️ Cette action est irréversible !",
            "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = faculteDAO.supprimer(id);
            if (ok) 
                JOptionPane.showMessageDialog(null, "✅ Faculté supprimée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            else    
                JOptionPane.showMessageDialog(null, "❌ Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return ok;
        }
        return false;
    }
    
    public List<Faculte> getAllFacultes() {
        return faculteDAO.getAll();
    }
    
    public Faculte getFaculteById(int id) {
        return faculteDAO.getById(id);
    }
}