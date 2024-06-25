package com.example.rikudo.Service;

import  com.example.rikudo.Entity.Categorie;
import com.example.rikudo.Repositor.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {
    private  CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Categorie recupererParNom (String nom) {
       return this.categorieRepository.findByNomCategorie(nom);
    }

    public Categorie creerCategorie(Categorie categorie) {
        return this.categorieRepository.save(categorie);
    }

    public List<Categorie> recupererCategories() {
       return this.categorieRepository.findAll();
    }

    public Categorie updateCategorie(Long idCategorie, Categorie categorie) {
        categorie.setIdCategorie(idCategorie);
        return this.categorieRepository.save(categorie);

    }
    public void deleteById (Long id) {
        this.categorieRepository.deleteCategorieByIdCategorie(id);
    }
}
