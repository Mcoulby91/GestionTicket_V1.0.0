package com.example.rikudo.Controlleur;


import  com.example.rikudo.Entity.BaseConnaissance;
import  com.example.rikudo.Service.BaseConnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("base")
public class BaseConnControlleur {
    @Autowired
    public BaseConnService baseConnService;

    @PostMapping("creer")
    public ResponseEntity<BaseConnaissance> createBaseConn(@RequestBody BaseConnaissance baseConnaissance) {
         this.baseConnService.creerBaseConnaissance(baseConnaissance) ;
         return ResponseEntity.ok().body(baseConnaissance);
    }

    @GetMapping("liste")
    public ResponseEntity<List<BaseConnaissance>> getBaseConnaissance() {
        List<BaseConnaissance> baseConnaissances = baseConnService.getAllBaseConnaissance();
        return ResponseEntity.ok(baseConnaissances);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<BaseConnaissance> updateBaseConn(@PathVariable int id, @RequestBody BaseConnaissance baseConnaissance) {
        baseConnaissance.setIdBaseConn(id);
        BaseConnaissance updateBase = baseConnService.updateBaseConn(id, baseConnaissance);
        return ResponseEntity.ok(updateBase);
    }

}
