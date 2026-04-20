package models;

import java.util.Date;

public class Faculte {
    private int idFac;
    private String nomFac;
    private String codeFac;
    private String adresse;
    private String telephone;
    private String email;
    private String doyen;
    private Date dateCreation;
    
    // Constructeurs
    public Faculte() {}
    
    public Faculte(String nomFac, String codeFac, String adresse, String telephone, String email, String doyen) {
        this.nomFac = nomFac;
        this.codeFac = codeFac;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.doyen = doyen;
    }
    
    // Getters et Setters
    public int getIdFac() { return idFac; }
    public void setIdFac(int idFac) { this.idFac = idFac; }
    
    public String getNomFac() { return nomFac; }
    public void setNomFac(String nomFac) { this.nomFac = nomFac; }
    
    public String getCodeFac() { return codeFac; }
    public void setCodeFac(String codeFac) { this.codeFac = codeFac; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getDoyen() { return doyen; }
    public void setDoyen(String doyen) { this.doyen = doyen; }
    
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    
    @Override
    public String toString() {
        return codeFac + " - " + nomFac;
    }
}