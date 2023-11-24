package com.mario.usuarios.utils;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mario.usuarios.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenUtil {

    private final JwtProperties jwtProperties = new JwtProperties(Keys.secretKeyFor(SignatureAlgorithm.HS256));

    public String generateAccessToken(Usuario usuario) {
    	final ObjectMapper objectMapper = new ObjectMapper();
    	
        return Jwts.builder()
        		.serializeToJsonWith(new JacksonSerializer<Map<String, ?>>(objectMapper))
        		.claim("user", usuario)
        		.setIssuedAt(new Date())
        		.signWith(jwtProperties.getSecret())
        		.setExpiration(new Date(System.currentTimeMillis()
                        + jwtProperties.getExpireMinutes()))
        		.compact();
    }

    public boolean validate(String token) {
        try {
			Jwts.parserBuilder().setSigningKey(jwtProperties.getSecret()).build().parseClaimsJws(token);
        	return true;
        } catch (SignatureException ex) {
            throw new JwtException(ex.getMessage());
        } catch (MalformedJwtException ex) {
        	throw new JwtException(ex.getMessage());
        } catch (ExpiredJwtException ex) {
        	throw new JwtException(ex.getMessage());
        } catch (UnsupportedJwtException ex) {
        	throw new JwtException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
        	throw new JwtException(ex.getMessage());
        }
    }

    public Usuario getUser(String token) {
		return Jwts.parserBuilder()
				.deserializeJsonWith(new JacksonDeserializer<Map<String, ?>>())
				.setSigningKey(jwtProperties.getSecret()).build().parseClaimsJws(token).getBody()
				.get("user", Usuario.class);
	}

    public Date getExpirationDate(Claims claims) {
        return claims.getExpiration();
    }

}