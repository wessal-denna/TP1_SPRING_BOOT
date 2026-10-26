package ma.ensaf.pharmacie.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.ensaf.pharmacie.model.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    // ===== Méthodes personnalisées =====

    List<Fournisseur> findByNom(String nom);

    List<Fournisseur> findByNomContainingIgnoreCase(String nom);

    List<Fournisseur> findByContact(String contact);

    List<Fournisseur> findByAdresseContaining(String adresse);

    // ===== Requête HQL avec @Query =====

    @Query("select f from Fournisseur f where f.adresse like %?1%")
    List<Fournisseur> chercherSelonLAdresse(String ville);

}