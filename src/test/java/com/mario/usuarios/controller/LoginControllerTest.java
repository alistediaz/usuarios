package com.mario.usuarios.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.repository.UsuarioRepository;
import com.mario.usuarios.service.UsuarioDetailsImpl;
import com.mario.usuarios.service.UsuarioDetailsServiceImpl;
import com.mario.usuarios.utils.JwtTokenUtil;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LoginControllerTest {
	@Autowired
    WebTestClient webTestClient;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
	private UsuarioRepository usuarioRepository;

    @Mock
	UsuarioDetailsServiceImpl usuarioDetailsServiceImpl;
    
    @InjectMocks
    private LoginController loginController;

    @Test
    public void testLoginSuccess() throws Exception {
    	UserDetails userDetails = new UsuarioDetailsImpl(1L, "testUser", "a2asfGfdfdf4");
    	String token = jwtTokenUtil.generateToken(userDetails);
    	
    	Usuario usuario = new Usuario("testUser","test@user.com", "a2asfGfdfdf4",null);
    	usuarioRepository.save(usuario);

		when(usuarioDetailsServiceImpl.loadUserByUsername("testUser")).thenReturn(userDetails);
    	
    	webTestClient
	        .get().uri("/login")
	        .headers(http -> http.setBearerAuth(token))
	        .exchange()
	        .expectStatus().isOk();
    }
    
    @Test
    public void testLoginWithoutToken() throws Exception {
    	webTestClient
	        .get().uri("/login")
	        .headers(http -> http.setBearerAuth(""))
	        .exchange()
	        .expectStatus().isUnauthorized();
    }
    
    @Test
    public void testLoginUsuarioNoExiste() throws Exception {
    	String tokenString = jwtTokenUtil.generateToken(
                new UsuarioDetailsImpl(1L, "test-user", "P4ssword"));
    	webTestClient
	        .get().uri("/login")
	        .headers(http -> http.setBearerAuth(tokenString))
	        .exchange()
	        .expectStatus().isUnauthorized();
    }
    
    @Test
    public void testLoginWithInvalidToken() throws Exception {
    	String tokenString = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    	webTestClient
	        .get().uri("/login")
	        .headers(http -> http.setBearerAuth(tokenString))
	        .exchange()
	        .expectStatus().isUnauthorized();
    }
    
    @Test
    public void testLoginWithOutdatedToken() throws Exception {
    	String tokenString = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJpYXQiOjE3MDA5MzE1NjMsImV4cCI6MTcwMDkzMTU4MX0.sTcmwsMmqFlYv8tzRByaszJHE7m3fQUXaBKFkHTKXCc";
    	webTestClient
	        .get().uri("/login")
	        .headers(http -> http.setBearerAuth(tokenString))
	        .exchange()
	        .expectStatus().isUnauthorized();
    }
}
