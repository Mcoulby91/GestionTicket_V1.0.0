package com.example.rikudo.Controlleur;

import com.example.rikudo.Entity.Statut;
import com.example.rikudo.Service.StatutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("statut")
public class StatutControlleur {

    @Autowired
    private StatutService statutService;

    @PostMapping("creer")
    public ResponseEntity<Statut> ajouterStatut(@RequestBody Statut statut) {
        this.statutService.creerStatut(statut);
        return ResponseEntity.ok().body(statut);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<Statut> modifierStatut(@PathVariable int id, @RequestBody Statut statut) {
        this.statutService.modifierStatut(id, statut);
        return ResponseEntity.ok().body(statut);
    }

    @DeleteMapping("supprimer/{id}")
    public void supprimerStatut(@PathVariable int id) {
        this.statutService.deleterStatut(id);
    }

}
