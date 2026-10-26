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

import ma.ensaf.pharmacie.dao.FournisseurRepository;
import ma.ensaf.pharmacie.model.Fournisseur;

@RestController
@RequestMapping(value = "/api/fournisseurs", produces = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE })
public class FournisseurRestController {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    // GET /api/fournisseurs
    @GetMapping
    public List<Fournisseur> getFournisseurs() {
        return fournisseurRepository.findAll();
    }

    // GET /api/fournisseurs/{id}
    @GetMapping("/{id}")
    public Fournisseur getFournisseur(@PathVariable("id") Long id) {
        return fournisseurRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fournisseur introuvable : " + id));
    }

    // POST /api/fournisseurs
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fournisseur addFournisseur(@RequestBody Fournisseur fournisseur) {
        System.out.println(fournisseur);
        fournisseur.setId(null);
        return fournisseurRepository.save(fournisseur);
    }

    // PUT /api/fournisseurs/{id}
    @PutMapping("/{id}")
    public Fournisseur modifierFournisseur(@PathVariable("id") Long id, @RequestBody Fournisseur fournisseur1) {
        Fournisseur fournisseur = fournisseurRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fournisseur introuvable : " + id));
        fournisseur.setNom(fournisseur1.getNom());
        fournisseur.setContact(fournisseur1.getContact());
        fournisseur.setAdresse(fournisseur1.getAdresse());
        return fournisseurRepository.save(fournisseur);
    }

    // DELETE /api/fournisseurs/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFournisseur(@PathVariable("id") Long id) {
        if (!fournisseurRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fournisseur introuvable : " + id);
        }
        try {
            fournisseurRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Suppression impossible : des produits utilisent ce fournisseur");
        }
    }

}