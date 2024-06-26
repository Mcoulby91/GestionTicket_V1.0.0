package com.example.rikudo.Repositor;

import com.example.rikudo.Entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MyUserRepository extends JpaRepository<MyUser, Long> {

   MyUser findByUsername (String username);
   Optional<MyUser> findByEmail (String email);
   MyUser findById(long id);
   MyUser findByRole (String role);
}
