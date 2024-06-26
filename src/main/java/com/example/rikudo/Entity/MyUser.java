package com.example.rikudo.Entity;

import com.example.rikudo.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String username;
    @Column (unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

}
