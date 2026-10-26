package ma.ensaf.pharmacie.model;

// Remplacement de javax par jakarta pour Spring Boot 3
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "detailProduit")
public class DetailProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fabricant")
    private String fabricant;

    @Column(name = "paysOrigine")
    private String paysOrigine;

    @Column(name = "composition", length = 1000)
    private String composition;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "indications", length = 1000)
    private String indications;

    // Cote inverse : DetailProduit (1) --- (1) Produit
    @JsonIgnoreProperties("detailProduit")
    @OneToOne(mappedBy = "detailProduit", fetch = FetchType.LAZY)
    private Produit produit;

    public DetailProduit() { }

    public DetailProduit(String fabricant, String paysOrigine, String composition, String dosage,
            String indications) {
        this.fabricant = fabricant;
        this.paysOrigine = paysOrigine;
        this.composition = composition;
        this.dosage = dosage;
        this.indications = indications;
    }

    public DetailProduit(Long id, String fabricant, String paysOrigine, String composition, String dosage,
            String indications) {
        this.id = id;
        this.fabricant = fabricant;
        this.paysOrigine = paysOrigine;
        this.composition = composition;
        this.dosage = dosage;
        this.indications = indications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFabricant() {
        return fabricant;
    }

    public void setFabricant(String fabricant) {
        this.fabricant = fabricant;
    }

    public String getPaysOrigine() {
        return paysOrigine;
    }

    public void setPaysOrigine(String paysOrigine) {
        this.paysOrigine = paysOrigine;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "DetailProduit {id:\"" + id + "\", fabricant:\"" + fabricant + "\", paysOrigine:\"" + paysOrigine
                + "\", composition:\"" + composition + "\", dosage:\"" + dosage + "\", indications:\""
                + indications + "\"}";
    }
}
