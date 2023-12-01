package com.mario.usuarios.service;

import java.util.Optional;

import com.mario.usuarios.classes.ResponseLogin;
import com.mario.usuarios.classes.ResponseSignUp;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;

public interface UsuarioService {
	Optional<Usuario> findByName(String username);
	ResponseLogin usuarioLogin() throws ValidacionException;
	ResponseSignUp usuarioSignUp(Usuario usuario) throws ValidacionException;
	Usuario save(Usuario usuario);
}
