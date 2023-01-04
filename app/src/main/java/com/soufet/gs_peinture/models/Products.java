package com.soufet.gs_peinture.models;

public class Products {
    private String nom,description,code_de_categorie,image,code_de_produit,categorie,id;
    private int quantity,seuil;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Products(String nom, String description, String code_de_categorie, String image, String code_de_produit, String categorie, String id, int quantity, int seuil) {
        this.nom = nom;
        this.description = description;
        this.code_de_categorie = code_de_categorie;
        this.image = image;
        this.code_de_produit = code_de_produit;
        this.categorie = categorie;
        this.id = id;
        this.quantity = quantity;
        this.seuil = seuil;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode_de_categorie() {
        return code_de_categorie;
    }

    public void setCode_de_categorie(String code_de_categorie) {
        this.code_de_categorie = code_de_categorie;
    }

    public String getCode_de_produit() {
        return code_de_produit;
    }

    public void setCode_de_produit(String code_de_produit) {
        this.code_de_produit = code_de_produit;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSeuil() {
        return seuil;
    }

    public void setSeuil(int seuil) {
        this.seuil = seuil;
    }
}
