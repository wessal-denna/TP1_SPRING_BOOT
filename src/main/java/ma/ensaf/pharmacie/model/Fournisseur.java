package ma.ensaf.pharmacie.model;

import java.util.ArrayList;
import java.util.List;

// Changement automatique de javax vers jakarta pour la compatibilité
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "fournisseur")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "contact")
    private String contact;

    @Column(name = "adresse")
    private String adresse;

    // Cote inverse : Fournisseur (1) --- (*) Produit
    @JsonIgnoreProperties("fournisseur")
    @OneToMany(mappedBy = "fournisseur", fetch = FetchType.LAZY)
    private List<Produit> produits = new ArrayList<Produit>();

    public Fournisseur() { }

    public Fournisseur(String nom, String contact, String adresse) {
        this.nom = nom;
        this.contact = contact;
        this.adresse = adresse;
    }

    public Fournisseur(Long id, String nom, String contact, String adresse) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Fournisseur {id:\"" + id + "\", nom:\"" + nom + "\", contact:\"" + contact
                + "\", adresse:\"" + adresse + "\"}";
    }
}
