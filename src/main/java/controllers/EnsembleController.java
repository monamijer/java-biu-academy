package controllers;

import models.Ensemble;
import models.EnsembleDAO;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EnsembleController {
    
    private EnsembleDAO ensembleDAO;
    
    public EnsembleController() {
        this.ensembleDAO = new EnsembleDAO();
    }
    
    // Méthode utilisée par EnsembleView pour l'ajout avec String gradeFkStr
    public boolean ajouterEnsemble(String nom, String prenom, String nationalite, String genre, 
                                   String gradeFkStr, String dateNaissanceStr, String telephone, String email) {
        if (nom == null || nom.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le prénom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        int gradeFk;
        try {
            gradeFk = Integer.parseInt(gradeFkStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Le grade est invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Date dateNaissance = null;
        if (dateNaissanceStr != null && !dateNaissanceStr.trim().isEmpty()) {
            try {
                dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissanceStr.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "❌ Format de date invalide ! Utilisez AAAA-MM-JJ", "Erreur", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        Ensemble enseignant = new Ensemble(nom.trim(), prenom.trim(), nationalite, genre, gradeFk, dateNaissance, telephone, email);
        boolean ok = ensembleDAO.ajouter(enseignant);
        if (ok) 
            JOptionPane.showMessageDialog(null, "✅ Enseignant ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }
    
    // Méthode utilisée par CoursView (non utilisée directement mais gardée pour compatibilité)
    public boolean ajouterEnseignant(String nom, String prenom, String nationalite, String genre, 
                                     int gradeFk, Date dateNaissance, String telephone, String email) {
        if (nom == null || nom.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le prénom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Ensemble enseignant = new Ensemble(nom.trim(), prenom.trim(), nationalite, genre, gradeFk, dateNaissance, telephone, email);
        boolean ok = ensembleDAO.ajouter(enseignant);
        if (ok) 
            JOptionPane.showMessageDialog(null, "✅ Enseignant ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }
    
    public boolean modifierEnsemble(Ensemble enseignant) {
        if (enseignant.getNom() == null || enseignant.getNom().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (enseignant.getPrenom() == null || enseignant.getPrenom().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le prénom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean ok = ensembleDAO.modifier(enseignant);
        if (ok) 
            JOptionPane.showMessageDialog(null, "✅ Enseignant modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }
    
    // Alias pour la compatibilité
    public boolean modifierEnseignant(Ensemble enseignant) {
        return modifierEnsemble(enseignant);
    }
    
    public boolean supprimerEnsemble(int id, String nomComplet) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Voulez-vous vraiment supprimer l'enseignant : " + nomComplet + " ?\n⚠️ Cette action est irréversible !",
            "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = ensembleDAO.supprimer(id);
            if (ok) 
                JOptionPane.showMessageDialog(null, "✅ Enseignant supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            else    
                JOptionPane.showMessageDialog(null, "❌ Erreur lors de la suppression (vérifiez les contraintes de clés étrangères).", "Erreur", JOptionPane.ERROR_MESSAGE);
            return ok;
        }
        return false;
    }
    
    // Alias pour la compatibilité
    public boolean supprimerEnseignant(int id, String nom, String prenom) {
        return supprimerEnsemble(id, prenom + " " + nom);
    }
    
    public List<Ensemble> getAllEnsembles() {
        return ensembleDAO.getAll();
    }
    
    // Alias pour la compatibilité
    public List<Ensemble> getAllEnseignants() {
        return getAllEnsembles();
    }
    
    public Ensemble getEnsembleById(int id) {
        return ensembleDAO.getById(id);
    }
    
    // Alias pour la compatibilité
    public Ensemble getEnseignantById(int id) {
        return getEnsembleById(id);
    }
}