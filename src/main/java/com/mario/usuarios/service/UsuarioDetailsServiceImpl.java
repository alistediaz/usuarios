package com.mario.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.repository.UsuarioService;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
	
    private final UsuarioService usuarioRepository;

    @Autowired
    public UsuarioDetailsServiceImpl(UsuarioService usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));

        return UsuarioDetailsImpl.build(usuario);
    }
}
