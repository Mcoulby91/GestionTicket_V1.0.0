package com.example.rikudo.Service;

import com.example.rikudo.Entity.MyUser;
import com.example.rikudo.Repositor.MyUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }


    private String[] getRoles(MyUser user) {
        if (user.getRole() == null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
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


}
