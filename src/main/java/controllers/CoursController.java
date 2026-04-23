package controllers;

import models.Cours;
import models.CoursDAO;
import models.Ensemble;
import models.EnsembleDAO;
import models.Faculte;
import models.FaculteDAO;
import javax.swing.*;
import java.util.List;

public class CoursController {

    private CoursDAO coursDAO;
    private EnsembleDAO ensembleDAO;
    private FaculteDAO faculteDAO;

    public CoursController() { 
        this.coursDAO = new CoursDAO();
        this.ensembleDAO = new EnsembleDAO();
        this.faculteDAO = new FaculteDAO();
    }

    public List<Ensemble> getAllProfesseurs() {
        return ensembleDAO.getAll();
    }
    
    public List<Faculte> getAllFacultes() {
        return faculteDAO.getAll();
    }

    public boolean ajouterCours(String code, String intitule, int vh, 
                                int professeurId, int faculteId, String description) {
        if (code == null || code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le code est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        if (intitule == null || intitule.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ L'intitulé est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        if (vh <= 0) {
            JOptionPane.showMessageDialog(null, "❌ Le volume horaire doit être positif !", "Erreur", JOptionPane.ERROR_MESSAGE); 
            return false;
        }

        Cours cours = new Cours(code.trim(), intitule.trim(), vh, professeurId, faculteId, description.trim());
        boolean ok = coursDAO.ajouter(cours);
        if (ok) 
            JOptionPane.showMessageDialog(null, "✅ Cours ajouté avec succès !\n\nCode: " + code + "\nIntitulé: " + intitule, 
                                        "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean modifierCours(Cours cours) {
        if (cours.getCode() == null || cours.getCode().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le code est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        if (cours.getIntitule() == null || cours.getIntitule().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ L'intitulé est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        if (cours.getVh() <= 0) {
            JOptionPane.showMessageDialog(null, "❌ Le volume horaire doit être positif !", "Erreur", JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        boolean ok = coursDAO.modifier(cours);
        if (ok) 
            JOptionPane.showMessageDialog(null, "✅ Cours modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean supprimerCours(int id, String code) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Voulez-vous vraiment supprimer le cours : " + code + " ?\n⚠️ Cette action est irréversible !",
            "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = coursDAO.supprimer(id);
            if (ok) 
                JOptionPane.showMessageDialog(null, "✅ Cours supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            else    
                JOptionPane.showMessageDialog(null, "❌ Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return ok;
        }
        return false;
    }

    public List<Cours> getAllCours() { 
        return coursDAO.getAll(); 
    }

    public Cours getCoursById(int id) { 
        return coursDAO.getById(id); 
    }
}