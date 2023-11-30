package com.mario.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mario.usuarios.model.Usuario;

import java.util.Optional;

public interface UsuarioService extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByName(String username);
}