package com.example.rikudo.Repositor;


import  com.example.rikudo.Entity.BaseConnaissance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseConnRepository extends JpaRepository<BaseConnaissance, Integer> {
    public BaseConnaissance findByIdBaseConn(int id);
}
