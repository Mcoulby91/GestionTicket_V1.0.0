package com.example.rikudo.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf((AbstractHttpConfigurer::disable))
                    .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/user/**").permitAll();
                    registry.requestMatchers(HttpMethod.POST,"/base/creer", "ticket/creer", "statut/creer", "categorie/creer", "prioriter").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.GET,"/base/liste", "ticket/listeTicket","ticket/ticketParId/", "categorie/liste").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.PUT,"/base/modifier/", "ticket/modifier/", "categorie/modifier/","statut/").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.DELETE,"ticket/supprimer/").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.POST,"/base/creer", "ticket/creer").hasRole("FORMATEUR");
                    registry.requestMatchers(HttpMethod.GET,"/base/liste", "ticket/listeTicket","ticket/ticketParId/").hasRole("FORMATEUR");
                    registry.requestMatchers(HttpMethod.PUT,"/base/modifier/", "ticket/modifier/").hasRole("FORMATEUR");
                    registry.requestMatchers(HttpMethod.DELETE,"ticket/supprimer/").hasRole("FORMATEUR");
                    registry.requestMatchers(HttpMethod.GET,"/base/liste", "ticket/listeTicket","ticket/ticketParId/").hasRole("APPRENANT");
                    registry.requestMatchers(HttpMethod.DELETE,"ticket/supprimer/").hasRole("APPRENANT");
                    registry.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }

   @Bean
   public UserDetailsService userDetailsService() {
        return userDetailsService;
   }

   @Bean
   public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       authProvider.setUserDetailsService(userDetailsService);
       authProvider.setPasswordEncoder(passwordEncoder());
       return authProvider;
   }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }







}
