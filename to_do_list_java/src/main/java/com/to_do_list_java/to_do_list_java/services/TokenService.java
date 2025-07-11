package com.to_do_list_java.to_do_list_java.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.to_do_list_java.to_do_list_java.models.AppUser;

@Service
public class TokenService 
{

    @Value("${jwt.secret.key}")
    private String secret;

    public String generateToken(AppUser appUser)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("to_do_list_backend")
                .withSubject(appUser.getLogin())
                .withExpiresAt(getExpirationTime()) 
                .sign(algorithm);

            return token;
        }catch(JWTCreationException e)
        {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token) 
    {
        try 
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                .withIssuer("to_do_list_backend")
                .build()
                .verify(token)
                .getSubject();

            return subject;
        } catch (JWTVerificationException e) 
        {
            return ""; // Token is invalid
        }
    }

    private Instant getExpirationTime() 
    {
        return LocalDateTime.now()
            .plusHours(1) // Token valid for 1 hour
            .toInstant(ZoneOffset.UTC);
    }

}