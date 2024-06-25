package com.example.rikudo.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                    registry.requestMatchers("/base/**", "/ticket/**", "/categorie/**", "/prioriter/**",
                            "/statut/**").hasRole("ADMIN");
                    registry.requestMatchers("/base/**","/reponse/**", "ticket/**", "statut/**").hasRole("FORMATEUR");
                        registry.requestMatchers("/base/liste", "ticket/**").hasRole("APPRENANT");
                    registry.anyRequest().authenticated();
                })
                .formLogin(formLogin -> formLogin.permitAll())
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