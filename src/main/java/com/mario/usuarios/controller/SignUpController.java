package com.mario.usuarios.controller;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mario.usuarios.domain.AuthRequest;
import com.mario.usuarios.service.UsuarioDetailsServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class SignUpController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioDetailsServiceImpl usuarioDetailsService;
    private final String jwtSecret = "EewdgIESEjAErqt"; 
    private final long jwtExpirationMs = 86400000; 

    public SignUpController(AuthenticationManager authenticationManager, UsuarioDetailsServiceImpl usuarioDetailsService) {
        this.authenticationManager = authenticationManager;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @PostMapping("/sign-up")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails usuarioDetails = usuarioDetailsService.loadUserByUsername(authRequest.getUsername());

        return Jwts.builder()
                .setSubject(usuarioDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
