package com.example.rikudo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_notification;
    private String message;
    private Date date;
    @ManyToOne
    private Ticket ticket;
    @ManyToOne
    private Formateur formateur;
    @ManyToOne
    private Apprenant apprenant;
}
