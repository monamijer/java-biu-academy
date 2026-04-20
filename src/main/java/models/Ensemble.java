package models;

import java.util.Date;

public class Ensemble {
    private int id;
    private String nom;
    private String prenom;
    private String nationalite;
    private String genre;
    private int gradeFk;
    private Date dateNaissance;
    private String telephone;
    private String email;
    private Date dateCreation;
    
    // Constructeurs
    public Ensemble() {}
    
    public Ensemble(String nom, String prenom, String nationalite, String genre, 
                   int gradeFk, Date dateNaissance, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.genre = genre;
        this.gradeFk = gradeFk;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.email = email;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getNationalite() { return nationalite; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public int getGradeFk() { return gradeFk; }
    public void setGradeFk(int gradeFk) { this.gradeFk = gradeFk; }
    
    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    @Override
    public String toString() {
        return getNomComplet();
    }
}