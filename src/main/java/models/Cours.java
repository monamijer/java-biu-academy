package models;

import java.util.Date;

public class Cours {
    private int id;
    private String code;
    private String intitule;
    private int vh;
    private int professeurId;
    private int faculteId;
    private String professeurNom;
    private String faculteNom;
    private String description;
    private Date dateCreation;
    
    // Constructeurs
    public Cours() {}
    
    public Cours(String code, String intitule, int vh, int professeurId, int faculteId, String description) {
        this.code = code;
        this.intitule = intitule;
        this.vh = vh;
        this.professeurId = professeurId;
        this.faculteId = faculteId;
        this.description = description;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    
    public int getVh() { return vh; }
    public void setVh(int vh) { this.vh = vh; }
    
    public int getProfesseurId() { return professeurId; }
    public void setProfesseurId(int professeurId) { this.professeurId = professeurId; }
    
    public int getFaculteId() { return faculteId; }
    public void setFaculteId(int faculteId) { this.faculteId = faculteId; }
    
    public String getProfesseurNom() { return professeurNom; }
    public void setProfesseurNom(String professeurNom) { this.professeurNom = professeurNom; }
    
    public String getFaculteNom() { return faculteNom; }
    public void setFaculteNom(String faculteNom) { this.faculteNom = faculteNom; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    
    @Override
    public String toString() {
        return code + " - " + intitule;
    }
}