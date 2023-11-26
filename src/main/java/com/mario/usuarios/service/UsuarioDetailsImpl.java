package com.mario.usuarios.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mario.usuarios.model.Usuario;

import java.util.Collection;

public class UsuarioDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final Long id;
    private final String username;
    private final String password;

    public UsuarioDetailsImpl(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static UsuarioDetailsImpl build(Usuario usuario) {
        return new UsuarioDetailsImpl(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword()
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getId() {
		return id;
	}
}
