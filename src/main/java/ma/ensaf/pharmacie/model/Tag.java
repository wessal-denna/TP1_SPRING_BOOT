package ma.ensaf.pharmacie.model;

import java.util.ArrayList;
import java.util.List;

// Remplacement de javax par jakarta pour Spring Boot 3
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    // Cote inverse : Tag (*) --- (*) Produit
    @JsonIgnoreProperties("tags")
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Produit> produits = new ArrayList<Produit>();

    public Tag() { }

    public Tag(String libelle) {
        this.libelle = libelle;
    }

    public Tag(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Tag {id:\"" + id + "\", libelle:\"" + libelle + "\"}";
    }
}
