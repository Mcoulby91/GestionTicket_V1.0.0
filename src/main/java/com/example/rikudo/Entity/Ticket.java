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
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_APPRENANT")
    private MyUser apprenant;
    @ManyToOne
    @JoinColumn(name = "ID_FORMATEUR")
    private MyUser formateur;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_STATUT")
    private Statut statut;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_CATEGORIE")
    private Categorie categorie;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_TICKET")
    private Prioriter prioriter;
    @ManyToOne
    @JoinColumn(name = "ID_REPONSETICKET")
    private ReponseTicket reponseTicket;

}
