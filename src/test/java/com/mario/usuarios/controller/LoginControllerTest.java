package com.mario.usuarios.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.mario.usuarios.service.UsuarioDetailsImpl;
import com.mario.usuarios.service.UsuarioService;
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
	/*@Autowired
    private MockMvc mockMvc;
*/
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void testLoginWithoutToken() throws Exception {
    	webTestClient
	        .get().uri("/login")
	        .headers(http -> http.setBearerAuth(""))
	        .exchange()
	        .expectStatus().isUnauthorized();
    }
    
    @Test
    public void testLoginWithToken() throws Exception {
    	String tokenString = jwtTokenUtil.generateToken(
                new UsuarioDetailsImpl(1L, "test-user", "P4ssword"));
    	webTestClient
	        .get().uri("/login")
	        .headers(http -> http.setBearerAuth(tokenString))
	        .exchange()
	        .expectStatus().isOk();
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
