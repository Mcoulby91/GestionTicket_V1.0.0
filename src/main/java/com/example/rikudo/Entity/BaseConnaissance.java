package com.example.rikudo.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BaseConnaissance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBaseConn;
    private String titre;
    private String description;
    private String date;
    @ManyToOne
    @JoinColumn(name = "ID_FORMATEUR")
    private Formateur formateur;
}
