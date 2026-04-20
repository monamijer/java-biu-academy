package models;

import java.util.Date;

public class Classe {
    private int idClasse;
    private String nomClasse;
    private int idFac;
    private String responsable;
    private String nomProjet;
    private String typeClasse;
    private String description;
    private Date dateCreation;
    
    // Constructeurs
    public Classe() {}
    
    public Classe(String nomClasse, int idFac, String responsable, 
                  String nomProjet, String typeClasse, String description) {
        this.nomClasse = nomClasse;
        this.idFac = idFac;
        this.responsable = responsable;
        this.nomProjet = nomProjet;
        this.typeClasse = typeClasse;
        this.description = description;
    }
    
    // Getters et Setters
    public int getIdClasse() { return idClasse; }
    public void setIdClasse(int idClasse) { this.idClasse = idClasse; }
    
    public String getNomClasse() { return nomClasse; }
    public void setNomClasse(String nomClasse) { this.nomClasse = nomClasse; }
    
    public int getIdFac() { return idFac; }
    public void setIdFac(int idFac) { this.idFac = idFac; }
    
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    
    public String getNomProjet() { return nomProjet; }
    public void setNomProjet(String nomProjet) { this.nomProjet = nomProjet; }
    
    public String getTypeClasse() { return typeClasse; }
    public void setTypeClasse(String typeClasse) { this.typeClasse = typeClasse; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    
    @Override
    public String toString() {
        return nomClasse;
    }
}