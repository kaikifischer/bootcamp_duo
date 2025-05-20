/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.service;

import br.edu.fesa.bootcampduo.model.UsuarioModel;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

/**
 *
 * @author Kaiki
 */
@Service
public class TokenService {

    private static final String SECRET_KEY = "MinhaChaveSecretaMuitoForte123"; // coloque no application.properties no futuro
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    public String generateToken(UsuarioModel user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}