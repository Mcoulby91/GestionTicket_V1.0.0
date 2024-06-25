package com.example.rikudo.Repositor;


import com.example.rikudo.Entity.Prioriter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReponseTicketRepository extends JpaRepository<Prioriter, Integer> {
}
