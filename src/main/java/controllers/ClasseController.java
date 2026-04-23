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
        if (nomClasse == null || nomClasse.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom de la classe est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int idFac;
        try { idFac = Integer.parseInt(idFacStr); }
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "❌ ID Fac invalide !", "Erreur", JOptionPane.ERROR_MESSAGE); return false; }

        Classe classe = new Classe(nomClasse.trim(), idFac, responsable.trim(), nomProjet.trim(), typeClasse, description.trim());
        boolean ok = classeDAO.ajouter(classe);
        if (ok) JOptionPane.showMessageDialog(null, "✅ Classe ajoutée avec succès !\n\n" + nomClasse, "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean modifierClasse(Classe classe) {
        if (classe.getNomClasse() == null || classe.getNomClasse().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Le nom de la classe est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        boolean ok = classeDAO.modifier(classe);
        if (ok) JOptionPane.showMessageDialog(null, "✅ Classe modifiée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        else    JOptionPane.showMessageDialog(null, "❌ Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean supprimerClasse(int id, String nomClasse) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Voulez-vous vraiment supprimer la classe : " + nomClasse + " ?\n⚠️ Cette action est irréversible !",
            "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = classeDAO.supprimer(id);
            if (ok) JOptionPane.showMessageDialog(null, "✅ Classe supprimée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            else    JOptionPane.showMessageDialog(null, "❌ Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return ok;
        }
        return false;
    }

    public List<Classe> getAllClasses() { return classeDAO.getAll(); }

    public Classe getClasseById(int id) { return classeDAO.getById(id); }
}
