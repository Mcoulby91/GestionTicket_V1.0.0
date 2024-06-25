package com.example.rikudo.Repositor;


import com.example.rikudo.Entity.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutRepository extends JpaRepository<Statut, Integer> {
    Statut findByTitre(String titre);
}
