package com.example.rikudo.Controlleur;

import com.example.rikudo.Entity.Categorie;
import com.example.rikudo.Service.CategorieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "categorie")
public class CategorieControlleur {


   private final CategorieService categorieService;

   public CategorieControlleur(CategorieService categorieService) {
       this.categorieService = categorieService;

  }

    @PostMapping("creer")
    public ResponseEntity<Categorie> ajouterCategorie(@RequestBody Categorie categorie) {
        this.categorieService.creerCategorie(categorie);
        return ResponseEntity.ok().body(categorie);
    }

    @GetMapping("liste")
    public ResponseEntity<List<Categorie>> recupererCategories() {
        List<Categorie> categories = categorieService.recupererCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<Categorie> modifierCategorie(@PathVariable Long id, @RequestBody Categorie categorie) {
      categorie.setIdCategorie(id);
      Categorie updateCategorie = categorieService.updateCategorie(id, categorie);
      return ResponseEntity.ok(updateCategorie);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Categorie> supprimerCategorie(@PathVariable Long id) {
       this.categorieService.deleteById(id);
       return ResponseEntity.noContent().build();
    }

}
