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

import static org.springframework.http.HttpMethod.*;

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
                    registry.requestMatchers(POST).hasRole("ADMIN");
                    registry.requestMatchers(PUT).hasRole("ADMIN");
                    registry.requestMatchers(GET).hasRole("ADMIN");
                    registry.requestMatchers(DELETE).hasRole("ADMIN");

                    registry.requestMatchers(POST, "/ticket/creer", "/base/creer").hasAnyRole("FORMATEUR","ADMIN");
                    registry.requestMatchers(PUT, "/ticket/modifier/", "/base/modifier/").hasAnyRole("FORMATEUR","ADMIN");
                    registry.requestMatchers(GET, "/ticket/listeTicket","/ticket/repondreTicket/","/base/liste").hasAnyRole("FORMATEUR","ADMIN");
                    registry.requestMatchers(DELETE,"/ticket/supprimer/").hasAnyRole("FORMATEUR","ADMIN");

                    registry.requestMatchers(POST,"/ticket/creer").hasAnyRole("APPRENANT","ADMIN");
                    registry.requestMatchers(PUT, "/ticket/modifier/").hasAnyRole("APPRENANT","ADMIN");
                    registry.requestMatchers(GET, "/ticket/listeTicket","/base/liste").hasAnyRole("APPRENANT","ADMIN");
                    registry.requestMatchers(DELETE,"/ticket/supprimer/").hasAnyRole("APPRENANT","ADMIN");

                    registry.requestMatchers("user/**").permitAll();
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
