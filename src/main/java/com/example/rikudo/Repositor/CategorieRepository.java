package com.example.rikudo.Repositor;


import com.example.rikudo.Entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Categorie findByNomCategorie(String name);

    void deleteCategorieByIdCategorie(Long id);
}
