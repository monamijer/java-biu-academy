package models;

import java.util.Date;

public class Cours {
    private int id;
    private String code;
    private boolean divisible;
    private int vh;
    private int mardiProjetFk;
    private String description;
    private Date dateCreation;
    
    // Constructeurs
    public Cours() {}
    
    public Cours(String code, boolean divisible, int vh, int mardiProjetFk, String description) {
        this.code = code;
        this.divisible = divisible;
        this.vh = vh;
        this.mardiProjetFk = mardiProjetFk;
        this.description = description;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public boolean isDivisible() { return divisible; }
    public void setDivisible(boolean divisible) { this.divisible = divisible; }
    
    public int getVh() { return vh; }
    public void setVh(int vh) { this.vh = vh; }
    
    public int getMardiProjetFk() { return mardiProjetFk; }
    public void setMardiProjetFk(int mardiProjetFk) { this.mardiProjetFk = mardiProjetFk; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    
    @Override
    public String toString() {
        return code + " - " + description;
    }
}