package com.example.rikudo.Service;

import com.example.rikudo.Entity.MyUser;
import com.example.rikudo.Enum.RoleEnum;
import com.example.rikudo.Repositor.MyUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MyUserDatailService implements UserDetailsService {

   @Autowired
   private MyUserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MyUser> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            var userObj = user.get();
            return  User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole().name())
                    .build();
        } else {
            throw new UsernameNotFoundException(email);
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



    private MyUser findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }



    public MyUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                // Récupérez l'utilisateur à partir de votre service utilisateur en utilisant le nom d'utilisateur
                return userRepository.findByEmail(username);
            } else if (principal instanceof MyUser) {
                return (MyUser) principal;
            }
        }
        return null;
    }

    public MyUser findByRole(RoleEnum role) {
        return this.userRepository.findByRole(String.valueOf(role));
    }
}
