package com.mario.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mario.usuarios.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}