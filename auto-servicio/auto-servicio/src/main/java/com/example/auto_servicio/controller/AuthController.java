/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.auto_servicio.controller;

import com.example.auto_servicio.dto.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-minutes}")
    private long expirationMinutes;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        // Usuario simulado (para probar JWT rápido)
        if (!"admin@admin.com".equals(request.getEmail()) || !"1234".equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(request.getEmail())
                .claim("role", "ADMIN")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMinutes * 60_000))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                
                .compact();
    }
}