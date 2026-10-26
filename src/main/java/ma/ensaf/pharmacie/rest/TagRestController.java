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

import ma.ensaf.pharmacie.dao.TagRepository;
import ma.ensaf.pharmacie.model.Tag;

@RestController
@RequestMapping(value = "/api/tags", produces = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE })
public class TagRestController {

    @Autowired
    private TagRepository tagRepository;

    // GET /api/tags
    @GetMapping
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    // GET /api/tags/{id}
    @GetMapping("/{id}")
    public Tag getTag(@PathVariable("id") Long id) {
        return tagRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag introuvable : " + id));
    }

    // POST /api/tags
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag addTag(@RequestBody Tag tag) {
        System.out.println(tag);
        tag.setId(null);
        return tagRepository.save(tag);
    }

    // PUT /api/tags/{id}
    @PutMapping("/{id}")
    public Tag modifierTag(@PathVariable("id") Long id, @RequestBody Tag tag1) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag introuvable : " + id));
        tag.setLibelle(tag1.getLibelle());
        return tagRepository.save(tag);
    }

    // DELETE /api/tags/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable("id") Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag introuvable : " + id);
        }
        try {
            tagRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Suppression impossible : des produits utilisent ce tag");
        }
    }

}