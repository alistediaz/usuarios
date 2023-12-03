package com.mario.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mario.usuarios.model.Phone;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockedStatic.Verification;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.mario.usuarios.classes.ResponseLogin;
import com.mario.usuarios.classes.ResponseSignUp;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.repository.UsuarioRepository;
import com.mario.usuarios.utils.JwtTokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;
    
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByName() {
        String username = "testUser";
        Usuario expectedUsuario = new Usuario("testUser", "test@user.com", "a2asfGfdfdf4", null);
        when(usuarioRepository.findByName(username)).thenReturn(Optional.of(expectedUsuario));

        Optional<Usuario> usuario = usuarioService.findByName(username);

        verify(usuarioRepository, times(1)).findByName(username);

        assertEquals(expectedUsuario, usuario.orElse(null));
    }

    @Test
    public void testUsuarioLogin() throws ValidacionException {
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Usuario expectedUsuario = new Usuario("testUser", "test@user.com", "a2asfGfdfdf4", null); 
        ResponseLogin expectedResponseLogin = new ResponseLogin(1, null, null, "testToken", true, "testUser",
        		"test@user.com", "a2asfGfdfdf4", null);
        
        
        try(MockedStatic<ResponseLogin> responseLoginMock = Mockito.mockStatic(ResponseLogin.class)){
        	
        	responseLoginMock.when(()-> ResponseLogin.build(Optional.of(expectedUsuario), "testToken")).thenReturn(expectedResponseLogin);
        	when(authentication.getName()).thenReturn("testUser");        
            when(usuarioService.findByName(any(String.class))).thenReturn(Optional.of(expectedUsuario));
            when(jwtTokenUtil.generateToken(any(UsuarioDetailsImpl.class))).thenReturn("testToken");

            ResponseLogin responseLogin = usuarioService.usuarioLogin();

            responseLoginMock.verify(()-> ResponseLogin.build(Optional.of(expectedUsuario), "testToken"));
            verify(usuarioRepository, times(1)).findByName("testUser");
            verify(jwtTokenUtil, times(1)).generateToken(any(UsuarioDetailsImpl.class));

            assertNotNull(responseLogin);
        }

    }

    @Test
    public void testUsuarioSignUp() throws ValidacionException {
        Usuario usuario = new Usuario("testUser", "test@user.com", "a2asfGfdfdf4", null); 
        when(usuarioRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        when(jwtTokenUtil.generateToken(any(UsuarioDetailsImpl.class))).thenReturn("testToken");

        ResponseSignUp responseSignUp = usuarioService.usuarioSignUp(usuario);

        verify(usuarioRepository, times(1)).findByName(any(String.class));
        verify(usuarioRepository, times(1)).save(usuario);
        verify(jwtTokenUtil, times(1)).generateToken(any(UsuarioDetailsImpl.class));

        assertNotNull(responseSignUp);
    }

    @Test
    public void testUsuarioSignUpWithPhones() throws ValidacionException {
        List<Phone> phones = new ArrayList<Phone>();
        phones.add(new Phone(87650009,7, "25"));
        phones.add(new Phone(87650019,6, "24"));
        Usuario usuario = new Usuario("testUser", "test@user.com", "a2asfGfdfdf4", phones);

        when(usuarioRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        when(jwtTokenUtil.generateToken(any(UsuarioDetailsImpl.class))).thenReturn("testToken");

        ResponseSignUp responseSignUp = usuarioService.usuarioSignUp(usuario);

        verify(usuarioRepository, times(1)).findByName(any(String.class));
        verify(usuarioRepository, times(1)).save(usuario);
        verify(jwtTokenUtil, times(1)).generateToken(any(UsuarioDetailsImpl.class));

        assertNotNull(responseSignUp);

    }
}
