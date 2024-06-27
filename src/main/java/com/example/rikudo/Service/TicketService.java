package com.example.rikudo.Service;


import com.example.rikudo.Entity.*;
import com.example.rikudo.Repositor.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CategorieService categorieService;
    @Autowired
    private PrioriterService prioriterService;
    @Autowired
    private StatutService statutService;
    @Autowired
    private MyUserDatailService myUserDatailService;
    @Autowired
    private SendEmailSevice sendEmailSevice;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(int id) {
        return ticketRepository.findAllById(id);
    }

    public Ticket createTicket(Ticket ticket) {
        //Recupérer l'utilisateur courant
        MyUser currentUser = myUserDatailService.getCurrentUser();
         String emailUtilisateurCourant = currentUser.getEmail();
         ticket.setReponseTicket("Pas encore repondue !");
         ticket.setApprenant(currentUser);

        // Récupérer et vérifier la catégorie
        Categorie category = this.categorieService.recupererParNom(ticket.getCategorie().getNomCategorie());
        if (category == null) {
            throw new IllegalArgumentException("La catégorie spécifiée n'existe pas.");
        }
        ticket.setCategorie(category);

        // Récupérer et vérifier la priorité
        Prioriter prioriter = this.prioriterService.recupererParNom(ticket.getPrioriter().getNomPrioriter());
        if (prioriter == null) {
            throw new IllegalArgumentException("La priorité spécifiée n'existe pas.");
        }
        ticket.setPrioriter(prioriter);

        // Récupérer et vérifier le statut
        Statut statut = this.statutService.recupererParTitre(ticket.getStatut().getTitre());
        if (statut == null) {
            throw new IllegalArgumentException("Le statut spécifié n'existe pas.");
        }
        ticket.setStatut(statut);

        ticket.getReponseTicket();

        // Sauvegarder le ticket
        Ticket saveTicket = ticketRepository.save(ticket);

        try {
            notification(emailUtilisateurCourant,"Nouveau Ticket Créer","Votre ticket a été soumis avec succès");
        } catch (Exception e) {
            System.err.println("Erreur d'envoie de notification "+e.getMessage());
        }

        return saveTicket;
    }


    public Ticket updateTicket(int id, Ticket ticket) {
       ticket.setId(id);
       return ticketRepository.save(ticket);
    }

    public void deleteTicket(int id) {
        ticketRepository.deleteById(id);
    }

    public Optional<Ticket> findAllById(int id) {
        return ticketRepository.findAllById(id);
    }

    public void notification (String email, String subject, String body){
                    sendEmailSevice.sendEmail(email, subject, body);
    }

    public Ticket repondreTicket(int id, Ticket ticket) {
        // Vérifier que le ticket n'est pas null
        if (ticket == null) {
            throw new IllegalArgumentException("Le ticket ne peut pas être null");
        }

        // Mettre à jour l'ID du ticket
        ticket.setId(id);

        // Récupérer l'utilisateur actuel
        MyUser currentUser = myUserDatailService.getCurrentUser();

        // Vérifier que l'utilisateur actuel n'est pas null
        if (currentUser == null) {
            throw new IllegalStateException("Utilisateur non authentifié");
        }

        // Assigner l'utilisateur actuel comme formateur du ticket
        ticket.setFormateur(currentUser);

        // Récupérer l'email de l'apprenant
        String emailApprenant = ticket.getApprenant().getEmail();

        // Vérifier que l'email de l'apprenant n'est pas null
        if (emailApprenant == null) {
            throw new IllegalStateException("L'apprenant doit avoir un email");
        }

        // Récupérer le nom d'utilisateur du formateur
        String formateur = currentUser.getUsername();

        // Enregistrer le ticket dans le repository
        Ticket savedTicket;
        try {
            savedTicket = ticketRepository.save(ticket);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du ticket", e);
        }

        // Envoyer une notification à l'apprenant
        try {
            notification(emailApprenant, "Réponse Ticket", "Votre ticket a été répondu avec succès par " + formateur);
        } catch (Exception e) {
            System.err.println("Erreur d'envoi de notification : " + e.getMessage());
        }

        // Retourner le ticket enregistré
        return savedTicket;
    }

}
