package com.example.rikudo.Repositor;

import com.example.rikudo.Entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyUserRepository extends JpaRepository<MyUser, Long> {

   MyUser findByUsername (String username);
   MyUser findByEmail (String email);
   MyUser findById(long id);
   MyUser findByRole (String role);
}
