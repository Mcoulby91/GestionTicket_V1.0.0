package com.example.rikudo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailSevice {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String receveur, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receveur);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
