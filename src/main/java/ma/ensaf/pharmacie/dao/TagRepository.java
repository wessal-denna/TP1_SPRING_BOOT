package ma.ensaf.pharmacie.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.ensaf.pharmacie.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

    // ===== Méthodes personnalisées =====

    List<Tag> findByLibelle(String libelle);

    List<Tag> findByLibelleContainingIgnoreCase(String libelle);

    // ===== Requête HQL avec @Query =====

    @Query("select t from Tag t where t.libelle like %?1%")
    List<Tag> chercherSelonLeLibelle(String motCle);

}