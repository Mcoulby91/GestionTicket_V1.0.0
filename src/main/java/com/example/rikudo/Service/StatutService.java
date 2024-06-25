package com.example.rikudo.Service;

import com.example.rikudo.Entity.Statut;
import com.example.rikudo.Repositor.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatutService {
    @Autowired
    private StatutRepository statutRepository;

    public Statut recupererParTitre (String titre) {
        return this.statutRepository.findByTitre(titre);
    }

    public Statut creerStatut (Statut statut) {
        return this.statutRepository.save(statut);
    }

    public Statut modifierStatut (int id, Statut statut) {
        statut.setIdStatut(id);
        return this.statutRepository.save(statut);
    }

    public void deleterStatut (int id) {
        this.statutRepository.deleteById(id);
    }

}
