package com.mario.usuarios.classes;

import com.mario.usuarios.model.Usuario;

public class ResponseSignUp extends Usuario {
	private String token;

	public ResponseSignUp(Usuario usuario) {
		this.setActive(usuario.isActive());
		this.setCreated(usuario.getCreated());
		this.setEmail(usuario.getEmail());
		this.setId(usuario.getId());
		this.setLastLogin(usuario.getLastLogin());
		this.setName(usuario.getName());
		this.setPassword(usuario.getPassword());
		this.setPhones(usuario.getPhones());
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
