package com.mario.usuarios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mario.usuarios.domain.Usuario;
import com.mario.usuarios.service.UsuarioService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private LoginController loginController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testGetUser() throws Exception {
        when(usuarioService.getUser(any(Long.class))).thenReturn(new Usuario("Juan Perez", "juan.perez@example.com", "securePassword"));
        
        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        Usuario newUsuario = new Usuario("Juan Perez", "juan.perez@example.com", "securePassword");
        when(usuarioService.createUser(any(Usuario.class))).thenReturn(newUsuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUsuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        Usuario updatedUsuario = new Usuario("Juan Perez", "juan.perez@example.com", "newPassword");
        when(usuarioService.updateUser(any(Long.class), any(Usuario.class))).thenReturn(updatedUsuario);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("newPassword"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isOk());
    }
}
