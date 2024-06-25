package com.example.rikudo.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private String date;
    @ManyToOne
    private MyUser apprenant;
    @ManyToOne
    private MyUser formateur;
    @ManyToOne
    private Statut statut;
    @ManyToOne
    private Categorie categorie;
    @ManyToOne
    private Prioriter prioriter;
    @ManyToOne
    private ReponseTicket reponseTicket;

}
