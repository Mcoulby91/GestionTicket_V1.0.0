package com.example.rikudo.Repositor;


import com.example.rikudo.Entity.Prioriter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrioriterRepository extends JpaRepository<Prioriter, Integer> {

    Prioriter findByNomPrioriter(String nom);
}
