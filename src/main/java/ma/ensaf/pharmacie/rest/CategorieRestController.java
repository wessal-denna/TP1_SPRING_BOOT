package ma.ensaf.pharmacie.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ma.ensaf.pharmacie.dao.CategorieRepository;
import ma.ensaf.pharmacie.model.Categorie;

@RestController
@RequestMapping(value = "/api/categories", produces = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE })
public class CategorieRestController {

    @Autowired
    private CategorieRepository categorieRepository;

    // GET /api/categories
    @GetMapping
    public List<Categorie> getCategories() {
        return categorieRepository.findAll();
    }

    // GET /api/categories/{id}
    @GetMapping("/{id}")
    public Categorie getCategorie(@PathVariable("id") Long id) {
        return categorieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie introuvable : " + id));
    }

    // POST /api/categories
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categorie addCategorie(@RequestBody Categorie categorie) {
        System.out.println(categorie);
        categorie.setId(null);
        return categorieRepository.save(categorie);
    }

    // PUT /api/categories/{id}
    @PutMapping("/{id}")
    public Categorie modifierCategorie(@PathVariable("id") Long id, @RequestBody Categorie categorie1) {
        Categorie categorie = categorieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie introuvable : " + id));
        categorie.setNom(categorie1.getNom());
        categorie.setDescription(categorie1.getDescription());
        return categorieRepository.save(categorie);
    }

    // DELETE /api/categories/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategorie(@PathVariable("id") Long id) {
        if (!categorieRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie introuvable : " + id);
        }
        try {
            categorieRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Suppression impossible : des produits utilisent cette categorie");
        }
    }

}