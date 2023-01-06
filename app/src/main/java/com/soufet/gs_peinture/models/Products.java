package com.soufet.gs_peinture.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Products.
 */
public class Products implements Serializable {
    private String nom,description,code_de_categorie,image,code_de_produit,categorie,id;
    private int quantity,seuil;
    /**
     * The Stars.
     */
    public Map<String, Boolean> stars = new HashMap<>();


    /**
     * To map map.
     *
     * @return the map
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nom", nom);
        result.put("description", description);



        return result;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Instantiates a new Products.
     */
    public Products() {
    }

    /**
     * Instantiates a new Products.
     *
     * @param nom         the nom
     * @param description the description
     */
    public Products(String nom,String description) {
        this.nom = nom;
        this.description = description;
    }

    /**
     * Instantiates a new Products.
     *
     * @param nom               the nom
     * @param description       the description
     * @param code_de_categorie the code de categorie
     * @param image             the image
     * @param code_de_produit   the code de produit
     * @param categorie         the categorie
     * @param id                the id
     * @param quantity          the quantity
     * @param seuil             the seuil
     */
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

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets nom.
     *
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets nom.
     *
     * @param nom the nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets code de categorie.
     *
     * @return the code de categorie
     */
    public String getCode_de_categorie() {
        return code_de_categorie;
    }

    /**
     * Sets code de categorie.
     *
     * @param code_de_categorie the code de categorie
     */
    public void setCode_de_categorie(String code_de_categorie) {
        this.code_de_categorie = code_de_categorie;
    }

    /**
     * Gets code de produit.
     *
     * @return the code de produit
     */
    public String getCode_de_produit() {
        return code_de_produit;
    }

    /**
     * Sets code de produit.
     *
     * @param code_de_produit the code de produit
     */
    public void setCode_de_produit(String code_de_produit) {
        this.code_de_produit = code_de_produit;
    }

    /**
     * Gets categorie.
     *
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Sets categorie.
     *
     * @param categorie the categorie
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets seuil.
     *
     * @return the seuil
     */
    public int getSeuil() {
        return seuil;
    }

    /**
     * Sets seuil.
     *
     * @param seuil the seuil
     */
    public void setSeuil(int seuil) {
        this.seuil = seuil;
    }
}
