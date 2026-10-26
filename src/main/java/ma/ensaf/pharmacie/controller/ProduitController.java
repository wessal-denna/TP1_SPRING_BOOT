package ma.ensaf.pharmacie.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ma.ensaf.pharmacie.dao.ProduitRepository;
import ma.ensaf.pharmacie.model.Produit;

@Controller
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    // Redirection de la racine vers la liste des produits
    @GetMapping("/")
    public String accueil() {
        return "redirect:/produits";
    }

    // GET /produits : affiche la liste complete des produits dans la vue
    @GetMapping("/produits")
    public String getProduits(Model model) {
        List<Produit> produits = produitRepository.findAll();
        model.addAttribute("produits", produits);
        model.addAttribute("aujourdhui", LocalDate.now());
        return "produits-liste";
    }
}
