package ma.ensaf.pharmacie.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.ensaf.pharmacie.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    // ===== Méthodes personnalisées (requêtes dérivées du nom de la méthode) =====

    List<Produit> findByNom(String nom);

    List<Produit> findByCodeBarre(String codeBarre);

    List<Produit> findByNomContainingIgnoreCase(String nom);

    List<Produit> findByNomStartingWith(String debut);

    List<Produit> findByPrixBetween(BigDecimal prixMin, BigDecimal prixMax);

    List<Produit> findByPrixLessThan(BigDecimal prix);

    List<Produit> findByPrixGreaterThan(BigDecimal prix);

    List<Produit> findByDateExpirationBefore(LocalDate date);

    List<Produit> findByDateExpirationAfter(LocalDate date);

    List<Produit> findByCategorieId(Long categorieId);

    List<Produit> findByFournisseurId(Long fournisseurId);

    List<Produit> findAllByOrderByPrixAsc();

    // ===== Requêtes HQL avec @Query =====

    @Query("select p from Produit p where p.prix between ?1 and ?2")
    List<Produit> chercherSelonLePrix(BigDecimal prixMin, BigDecimal prixMax);

    @Query("select p from Produit p where p.categorie.nom = ?1")
    List<Produit> chercherSelonLaCategorie(String nomCategorie);

    @Query("select p from Produit p where p.fournisseur.nom = ?1")
    List<Produit> chercherSelonLeFournisseur(String nomFournisseur);

    @Query("select distinct p from Produit p join p.tags t where t.libelle = ?1")
    List<Produit> chercherSelonLeTag(String libelle);

    @Query("select p from Produit p where p.dateExpiration <= ?1 order by p.dateExpiration asc")
    List<Produit> chercherProduitsExpirantAvant(LocalDate date);

}