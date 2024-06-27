package com.example.rikudo.Controlleur;


import com.example.rikudo.Service.SendEmailSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailControlleur {

    @Autowired
    private SendEmailSevice sendEmailSevice;

    @GetMapping
    public String sendEmail() {
        sendEmailSevice.sendEmail("manacoulibaly44@gmail.com", "Test Subject", "Test Body");
        return "Email sent succefully";
    }
}
