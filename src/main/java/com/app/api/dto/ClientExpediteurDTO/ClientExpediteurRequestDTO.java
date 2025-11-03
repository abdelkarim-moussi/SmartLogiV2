package com.app.api.dto.ClientExpediteurDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ClientExpediteurRequestDTO {
    @NotNull(message = "entrer un nom valide")
    private String nom;
    @NotNull(message = "entrer un prenom valide")
    private String prenom;
    @Pattern(regexp = "0+[0-9]{9}",message = "telephone invalid (format : 0 + 9 nombre apré , ex : 0678954321")
    private String telephone;
    @Email
    private String email;
    @NotNull(message = "entrer une adresse valide")
    private String adresse;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
