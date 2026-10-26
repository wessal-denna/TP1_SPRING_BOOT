package ma.ensaf.pharmacie.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Remplacement global de javax par jakarta pour Spring Boot 3
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "prix", precision = 10, scale = 2)
    private BigDecimal prix;

    @Column(name = "code_barre")
    private String codeBarre;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    // Produit (*) --- (1) Categorie
    @JsonIgnoreProperties("produits")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    // Produit (*) --- (1) Fournisseur
    @JsonIgnoreProperties("produits")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    // Produit (1) --- (1) DetailProduit
    @JsonIgnoreProperties("produit")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "detail_id")
    private DetailProduit detailProduit;

    // Produit (*) --- (*) Tag
    @JsonIgnoreProperties("produits")
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(name = "produit_tag",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<Tag>();

    public Produit() { }

    public Produit(String nom, String description, BigDecimal prix, String codeBarre, LocalDate dateExpiration) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.codeBarre = codeBarre;
        this.dateExpiration = dateExpiration;
    }

    public Produit(String nom, String description, BigDecimal prix, String codeBarre, LocalDate dateExpiration,
            Categorie categorie, Fournisseur fournisseur, DetailProduit detailProduit, List<Tag> tags) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.codeBarre = codeBarre;
        this.dateExpiration = dateExpiration;
        this.categorie = categorie;
        this.fournisseur = fournisseur;
        this.detailProduit = detailProduit;
        this.tags = tags;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public DetailProduit getDetailProduit() {
        return detailProduit;
    }

    public void setDetailProduit(DetailProduit detailProduit) {
        this.detailProduit = detailProduit;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Produit {id:\"" + id + "\", nom:\"" + nom + "\", description:\"" + description + "\", prix:\""
                + prix + "\", codeBarre:\"" + codeBarre + "\", dateExpiration:\"" + dateExpiration + "\"}";
    }
}
