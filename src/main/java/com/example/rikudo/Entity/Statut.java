package com.example.rikudo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStatut;
    private String titre;
    @OneToMany
    private List<Ticket> ticket;
}
