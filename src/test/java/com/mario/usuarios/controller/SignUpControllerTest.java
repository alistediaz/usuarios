package com.mario.usuarios.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mario.usuarios.classes.ResponseSignUp;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.repository.UsuarioRepository;
import com.mario.usuarios.utils.JwtTokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {

    @InjectMocks
    private SignUpController signUpController;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void testSignUp() throws ValidacionException {
    	Usuario usuario = new Usuario("testUser","test@user.com", "a2asfGfdfdf4",null);
         
        when(usuarioRepository.findByName("testUser")).thenReturn(Optional.empty());
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(jwtTokenUtil.generateToken(any())).thenReturn("testToken");
    
        ResponseSignUp responseSignUp = signUpController.signUp(usuario);
        verify(usuarioRepository, times(1)).save(usuario);

        assertEquals("testToken", responseSignUp.getToken());

    }

    @Test
    public void testSignUpWithExistingUser() throws ValidacionException {
        Usuario usuario = new Usuario();
        usuario.setName("existingUser");

        Optional<Usuario> usuarioExistente = Optional.of(new Usuario());
        when(usuarioRepository.findByName(usuario.getName())).thenReturn(usuarioExistente);

        Assertions.assertThrows(ValidacionException.class, () -> signUpController.signUp(usuario));
    }
}