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

import io.jsonwebtoken.io.IOException;

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
                                    FilterChain chain)
            throws ServletException, IOException {

    	final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    	
        if (header.isEmpty() || !header.startsWith("Bearer ")) {
            try {
				chain.doFilter(request, response);
			} catch (java.io.IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return;
        }

        final String token = header.split(" ")[1].trim();
        
        if (!jwtTokenUtil.validate(token)) {
            try {
				chain.doFilter(request, response);
			} catch (java.io.IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return;
        }

        // Get user identity and set it on the spring security context
        UserDetails userDetails = (UserDetails) usuarioRepository
            .findByUsername(jwtTokenUtil.getUser(token).getUsername())
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
        try {
			chain.doFilter(request, response);
		} catch (java.io.IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}