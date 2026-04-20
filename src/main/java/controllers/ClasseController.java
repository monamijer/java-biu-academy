package controllers;

import models.Classe;
import models.ClasseDAO;
import javax.swing.*;
import java.util.List;

public class ClasseController {
    
    private ClasseDAO classeDAO;
    
    public ClasseController() {
        this.classeDAO = new ClasseDAO();
    }
    
    public boolean ajouterClasse(String nomClasse, String idFacStr, String responsable,
                                 String nomProjet, String typeClasse, String description) {
        // Validation
        if (nomClasse == null || nomClasse.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom de la classe est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        int idFac;
        try {
            idFac = Integer.parseInt(idFacStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ ID Fac doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Classe classe = new Classe(nomClasse.trim(), idFac, responsable.trim(), 
                                   nomProjet.trim(), typeClasse, description.trim());
        boolean success = classeDAO.ajouter(classe);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "✅ Classe ajoutée avec succès !\n\n" + nomClasse, 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
        
        return success;
    }
    
    public List<Classe> getAllClasses() {
        return classeDAO.getAll();
    }
}