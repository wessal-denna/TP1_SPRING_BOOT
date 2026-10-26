package ma.ensaf.pharmacie.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.ensaf.pharmacie.model.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    // ===== Méthodes personnalisées =====

    List<Categorie> findByNom(String nom);

    List<Categorie> findByNomContainingIgnoreCase(String nom);

    List<Categorie> findAllByOrderByNomAsc();

    // ===== Requête HQL avec @Query =====

    @Query("select c from Categorie c where c.description like %?1%")
    List<Categorie> chercherSelonLaDescription(String motCle);

}