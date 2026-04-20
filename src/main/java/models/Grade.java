package models;

import java.util.Date;

public class Grade {
    private int idGrade;
    private String nomGrade;
    private String niveau;
    private String description;
    private Date dateCreation;
    
    // Constructeurs
    public Grade() {}
    
    public Grade(String nomGrade, String niveau, String description) {
        this.nomGrade = nomGrade;
        this.niveau = niveau;
        this.description = description;
    }
    
    // Getters et Setters
    public int getIdGrade() { return idGrade; }
    public void setIdGrade(int idGrade) { this.idGrade = idGrade; }
    
    public String getNomGrade() { return nomGrade; }
    public void setNomGrade(String nomGrade) { this.nomGrade = nomGrade; }
    
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    
    @Override
    public String toString() {
        return nomGrade + " (" + niveau + ")";
    }
}