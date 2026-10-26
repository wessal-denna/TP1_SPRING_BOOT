package ma.ensaf.pharmacie.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.ensaf.pharmacie.model.DetailProduit;

public interface DetailProduitRepository extends JpaRepository<DetailProduit, Long> {

    // ===== Méthodes personnalisées =====

    List<DetailProduit> findByFabricant(String fabricant);

    List<DetailProduit> findByPaysOrigine(String paysOrigine);

    List<DetailProduit> findByDosage(String dosage);

    List<DetailProduit> findByCompositionContainingIgnoreCase(String composition);

    // ===== Requête HQL avec @Query =====

    @Query("select d from DetailProduit d where d.fabricant = ?1 and d.paysOrigine = ?2")
    List<DetailProduit> chercherSelonFabricantEtPays(String fabricant, String paysOrigine);

}