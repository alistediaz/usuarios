package com.mario.usuarios.service;

import com.mario.usuarios.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private com.mario.usuarios.repository.UsuarioService usuarioRepository;

    public Optional<Usuario> findByName(String username) {
        return  usuarioRepository.findByName(username);
    }
}
