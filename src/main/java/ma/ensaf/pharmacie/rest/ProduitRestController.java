package ma.ensaf.pharmacie.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ma.ensaf.pharmacie.dao.CategorieRepository;
import ma.ensaf.pharmacie.dao.DetailProduitRepository;
import ma.ensaf.pharmacie.dao.FournisseurRepository;
import ma.ensaf.pharmacie.dao.ProduitRepository;
import ma.ensaf.pharmacie.dao.TagRepository;
import ma.ensaf.pharmacie.model.Categorie;
import ma.ensaf.pharmacie.model.DetailProduit;
import ma.ensaf.pharmacie.model.Fournisseur;
import ma.ensaf.pharmacie.model.Produit;
import ma.ensaf.pharmacie.model.Tag;

@RestController
@RequestMapping(value = "/api/produits", produces = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE })
public class ProduitRestController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private DetailProduitRepository detailProduitRepository;

    @Autowired
    private TagRepository tagRepository;

    // ===== GET /api/produits : lister tous les produits =====
    @GetMapping
    public List<Produit> getProduits() {
        return produitRepository.findAll();
    }

    // ===== GET /api/produits/{id} : afficher un produit =====
    @GetMapping("/{id}")
    public Produit getProduit(@PathVariable("id") Long id) {
        return produitRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable : " + id));
    }

    // ===== POST /api/produits : ajouter un produit =====
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Produit addProduit(@RequestBody Produit produit) {
        System.out.println(produit);

        // On force une creation (jamais une mise a jour) meme si le client envoie un id
        produit.setId(null);

        // 1. Rattacher les entites existantes (categorie, fournisseur, tags)
        produit.setCategorie(resoudreCategorie(produit.getCategorie()));
        produit.setFournisseur(resoudreFournisseur(produit.getFournisseur()));
        produit.setTags(resoudreTags(produit.getTags()));

        // 2. Sauvegarder (ou recuperer) le DetailProduit avant le produit
        produit.setDetailProduit(resoudreDetail(produit.getDetailProduit(), null));

        // 3. Sauvegarder le produit
        return produitRepository.save(produit);
    }

    // ===== PUT /api/produits/{id} : modifier un produit =====
    @PutMapping("/{id}")
    @Transactional
    public Produit modifierProduit(@PathVariable("id") Long id, @RequestBody Produit produit1) {
        Produit produit = produitRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable : " + id));

        produit.setNom(produit1.getNom());
        produit.setDescription(produit1.getDescription());
        produit.setPrix(produit1.getPrix());
        produit.setCodeBarre(produit1.getCodeBarre());
        produit.setDateExpiration(produit1.getDateExpiration());
        produit.setCategorie(resoudreCategorie(produit1.getCategorie()));
        produit.setFournisseur(resoudreFournisseur(produit1.getFournisseur()));
        produit.setTags(resoudreTags(produit1.getTags()));
        produit.setDetailProduit(resoudreDetail(produit1.getDetailProduit(), produit.getDetailProduit()));

        return produitRepository.save(produit);
    }

    // ===== DELETE /api/produits/{id} : supprimer un produit =====
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduit(@PathVariable("id") Long id) {
        if (!produitRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable : " + id);
        }
        produitRepository.deleteById(id);
    }

    // ===== Routes de recherche (methodes personnalisees du DAO) =====

    // GET /api/produits/recherche?nom=doli
    @GetMapping("/recherche")
    public List<Produit> rechercherParNom(@RequestParam("nom") String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom);
    }

    // GET /api/produits/prix?min=20&max=45
    @GetMapping("/prix")
    public List<Produit> rechercherParPrix(@RequestParam("min") BigDecimal min,
            @RequestParam("max") BigDecimal max) {
        return produitRepository.findByPrixBetween(min, max);
    }

    // GET /api/produits/expiration?avant=2026-06-01
    @GetMapping("/expiration")
    public List<Produit> rechercherExpirantAvant(
            @RequestParam("avant") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate avant) {
        return produitRepository.findByDateExpirationBefore(avant);
    }

    // ===================================================================
    // Methodes privees : rattachement des entites imbriquees
    // (meme principe que la boucle sur les adresses dans le cours)
    // ===================================================================

    private Categorie resoudreCategorie(Categorie categorie) {
        if (categorie == null) {
            return null;
        }
        if (categorie.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La categorie doit etre identifiee par son id");
        }
        return categorieRepository.findById(categorie.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Categorie introuvable : " + categorie.getId()));
    }

    private Fournisseur resoudreFournisseur(Fournisseur fournisseur) {
        if (fournisseur == null) {
            return null;
        }
        if (fournisseur.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le fournisseur doit etre identifie par son id");
        }
        return fournisseurRepository.findById(fournisseur.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Fournisseur introuvable : " + fournisseur.getId()));
    }

    private List<Tag> resoudreTags(List<Tag> tags) {
        List<Tag> resultat = new ArrayList<Tag>();
        if (tags == null) {
            return resultat;
        }
        for (Tag tag : tags) {
            if (tag.getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Chaque tag doit etre identifie par son id");
            }
            Tag t = tagRepository.findById(tag.getId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Tag introuvable : " + tag.getId()));
            if (!resultat.contains(t)) {
                resultat.add(t);
            }
        }
        return resultat;
    }

    /**
     * - detail == null           : on conserve le detail existant (null a la creation)
     * - detail avec id           : on rattache le detail existant en base
     * - detail sans id + existant: on met a jour le detail existant (pas d'orphelin)
     * - detail sans id           : on sauvegarde un nouveau detail
     */
    private DetailProduit resoudreDetail(DetailProduit detail, DetailProduit existant) {
        if (detail == null) {
            return existant;
        }
        if (detail.getId() != null) {
            return detailProduitRepository.findById(detail.getId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "DetailProduit introuvable : " + detail.getId()));
        }
        if (existant != null) {
            existant.setFabricant(detail.getFabricant());
            existant.setPaysOrigine(detail.getPaysOrigine());
            existant.setComposition(detail.getComposition());
            existant.setDosage(detail.getDosage());
            existant.setIndications(detail.getIndications());
            return detailProduitRepository.save(existant);
        }
        return detailProduitRepository.save(detail);
    }

}