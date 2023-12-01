package com.mario.usuarios.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mario.usuarios.classes.ResponseSignUp;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.service.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {

    @Mock
    private UsuarioServiceImpl usuarioService;

    @InjectMocks
    private SignUpController signUpController;

    @Before
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp() throws ValidacionException {
    	Usuario usuario = new Usuario("testUser","test@user.com", "a2asfGfdfdf4",null);
        ResponseSignUp expectedResponse = new ResponseSignUp(usuario);
        when(usuarioService.usuarioSignUp(any(Usuario.class))).thenReturn(expectedResponse);

        ResponseSignUp responseSignUp = signUpController.signUp(usuario);
        verify(usuarioService, times(1)).usuarioSignUp(any(Usuario.class));

        assertEquals(expectedResponse, responseSignUp);
    }

    @Test
    public void testSignUpWithExistingUser() throws ValidacionException {
        Usuario usuario = new Usuario();
        usuario.setName("existingUser");

        when(usuarioService.usuarioSignUp(any(Usuario.class))).thenThrow(ValidacionException.class);

        Assertions.assertThrows(ValidacionException.class, () -> signUpController.signUp(usuario));
    }
}