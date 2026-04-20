package controllers;

import models.Ensemble;
import models.EnsembleDAO;
import javax.swing.*;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class EnsembleController {
    
    private EnsembleDAO ensembleDAO;
    private SimpleDateFormat dateFormat;
    
    public EnsembleController() {
        this.ensembleDAO = new EnsembleDAO();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    public boolean ajouterEnsemble(String nom, String prenom, String nationalite, 
                                   String genre, String gradeFkStr, String dateNaissanceStr,
                                   String telephone, String email) {
        // Validation
        if (nom == null || nom.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (prenom == null || prenom.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le prénom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (nationalite == null || nationalite.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ La nationalité est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (genre == null) {
            JOptionPane.showMessageDialog(null, "❌ Le genre est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        int gradeFk;
        try {
            gradeFk = Integer.parseInt(gradeFkStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Le grade doit être un nombre valide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Conversion de la date
        Date dateNaissance = null;
        if (dateNaissanceStr != null && !dateNaissanceStr.trim().isEmpty()) {
            try {
                dateNaissance = dateFormat.parse(dateNaissanceStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "❌ Format de date invalide ! Utilisez AAAA-MM-JJ", "Erreur", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        // Création de l'objet Ensemble avec tous les paramètres
        Ensemble ensemble = new Ensemble(
            nom.trim(), 
            prenom.trim(), 
            nationalite.trim(), 
            genre, 
            gradeFk,
            dateNaissance,
            telephone != null ? telephone.trim() : "",
            email != null ? email.trim() : ""
        );
        
        boolean success = ensembleDAO.ajouter(ensemble);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "✅ Enseignant ajouté avec succès !\n\n" + 
                "Nom complet: " + prenom + " " + nom + "\n" +
                "Nationalité: " + nationalite + "\n" +
                "Genre: " + genre, 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'ajout dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
    
    public List<Ensemble> getAllEnsembles() {
        return ensembleDAO.getAll();
    }
    
    public Ensemble getEnsembleById(int id) {
        return ensembleDAO.getById(id);
    }
    
    public boolean modifierEnsemble(Ensemble ensemble) {
        // Validation
        if (ensemble.getNom() == null || ensemble.getNom().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (ensemble.getPrenom() == null || ensemble.getPrenom().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le prénom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean success = ensembleDAO.modifier(ensemble);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "✅ Enseignant modifié avec succès !", 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Erreur lors de la modification", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
    
    public boolean supprimerEnsemble(int id, String nomComplet) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Voulez-vous vraiment supprimer cet enseignant ?\n\n" +
            "Nom: " + nomComplet + "\n\n" +
            "⚠️ Attention : Cette action est irréversible !",
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = ensembleDAO.supprimer(id);
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "✅ Enseignant supprimé avec succès", 
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "❌ Erreur lors de la suppression", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            return success;
        }
        return false;
    }
    
    // Méthode utilitaire pour formater une date
    public String formatDate(Date date) {
        if (date == null) return "";
        return dateFormat.format(date);
    }
    
    // Méthode utilitaire pour parser une date
    public Date parseDate(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        return dateFormat.parse(dateStr);
    }
}