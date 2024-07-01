package com.example.rikudo.Controlleur;

import com.example.rikudo.Entity.MyUser;
import com.example.rikudo.Repositor.AdminRepository;
import com.example.rikudo.Repositor.MyUserRepository;
import com.example.rikudo.Service.MyUserDatailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControlleur {
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MyUserDatailService myUserDatailService;
    @Autowired
    private AdminRepository adminRepository;


    @PostMapping("/creer")
    public MyUser createUser(@RequestBody MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return myUserRepository.save(myUser);
    }

    @GetMapping("/findById/{id}")
    public MyUser getUser(@PathVariable int id) {
        return myUserRepository.findById(id);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<MyUser> modifierUser(@PathVariable Long id, @RequestBody MyUser user) {
        this.myUserDatailService.modifyUser(id,user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/listeUser")
    public List<MyUser> listeUser() {
        return myUserRepository.findAll();
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerUser(@PathVariable Long id) {
        MyUser user = this.myUserDatailService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        this.myUserDatailService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
