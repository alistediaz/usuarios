package com.mario.usuarios.utils;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mario.usuarios.repository.UsuarioRepository;

import io.jsonwebtoken.JwtException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UsuarioRepository usuarioRepository;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
    		UsuarioRepository usuarioRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws java.io.IOException, ServletException
           {

    	final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    	
        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
        	throw new JwtException("Token is not available.");
        }

        final String token = header.split(" ")[1].trim();
        
        if (!jwtTokenUtil.validate(token)) {
        	throw new JwtException("Token is not valid.");
        }

        UserDetails userDetails = (UserDetails) usuarioRepository
            .findByUsername(jwtTokenUtil.getUsernameFromToken(token))
            .orElse(null);

        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                    List.of() : userDetails.getAuthorities()
            );

        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
		chain.doFilter(request, response);
		
    }

}