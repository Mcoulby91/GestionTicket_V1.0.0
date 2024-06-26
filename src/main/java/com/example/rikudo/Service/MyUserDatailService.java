package com.example.rikudo.Service;

import com.example.rikudo.Entity.MyUser;
import com.example.rikudo.Repositor.MyUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class MyUserDatailService implements UserDetailsService {

   @Autowired
   private MyUserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return  User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole().name())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }




    public MyUser modifyUser(Long id, MyUser user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public List<MyUser> findAllUsers() {
        return this.userRepository.findAll();
    }

    public MyUser deleteUser(Long id) {
        Optional<MyUser> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            MyUser user = userOptional.get();
            this.userRepository.deleteById(id);
            return user;
        } else {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'id : " + id);
        }
    }

    public MyUser findUserById(Long id) {
        return this.userRepository.findById(id).get();
    }

    public MyUser findByRole (String role) {
        return this.userRepository.findByRole(role);
    }

    public MyUser getCurrentUser() {
        // Récupère l'utilisateur courant du contexte de sécurité
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Si l'utilisateur est une instance de UserDetails, on le cast
        if (principal instanceof UserDetails) { // Supposons que CustomUserDetails implémente UserDetails et contient l'ID de l'utilisateur
            Long userId = (long) ((UserDetails) principal).getClass().getModifiers();
            // Recherche l'utilisateur dans la base de données par son ID
            return findUserById(userId);
        } else {
            throw new IllegalStateException("Principal n'est pas une instance de CustomUserDetails");
        }
    }

    private MyUser findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
