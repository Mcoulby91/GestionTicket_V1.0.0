package com.example.rikudo.Controlleur;


import com.example.rikudo.Entity.MyUser;
import com.example.rikudo.Entity.Ticket;
import com.example.rikudo.Service.MyUserDatailService;
import com.example.rikudo.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ticket")
public class TicketControlleur {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MyUserDatailService myUserDatailService;

    @GetMapping("listeTicket")
    public ResponseEntity<Optional<Ticket>> getAllTickets() {
        MyUser userId = myUserDatailService.getCurrentUser();
        int id = Math.toIntExact(userId.getId());
        Optional<Ticket> tickets = ticketService.findAllById(id);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("creer")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);

        return ResponseEntity.ok(createdTicket);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody Ticket ticket) {
        Optional<Ticket> existingTicket = ticketService.findAllById(id);
        if (!existingTicket.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ticket.setId(id);
        Ticket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @PutMapping("repondreTicket/{id}")
    public ResponseEntity<Ticket> repondreTicket(@PathVariable int id, @RequestBody Ticket ticket) {
        Optional<Ticket> existingTicket = ticketService.findAllById(id);
        if (!existingTicket.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ticket.setId(id);
        Ticket updatedTicket = ticketService.repondreTicket(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
