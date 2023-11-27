package com.mario.usuarios.classes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mario.usuarios.model.Phone;
import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.service.UsuarioDetailsImpl;

public class ResponseLogin {
	
	private long id;
	private Date created;
	private Date lastLogin;
	private String token;
	private boolean isActive;
	private String name;
	private String email;
	private String password;
	private List<Phone> phones;
	
	public ResponseLogin(long id, Date localDateTime, Date lastLogin, String token, boolean isActive, String name,
			String email, String password, List<Phone> phones) {
		this.id = id;
		this.created = localDateTime;
		this.lastLogin = lastLogin;
		this.token = token;
		this.isActive = isActive;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phones = phones;
	}

	public ResponseLogin() {
	}

	 public static ResponseLogin build(Optional<Usuario> usuario, String token) {
		 Usuario responseUsuario = usuario.get();
		 
	        return new ResponseLogin(
	        		responseUsuario.getId(),
	        		responseUsuario.getCreated(),
	        		responseUsuario.getLastLogin(),
	        		token,
	        		responseUsuario.isActive(),
	        		responseUsuario.getName(),
	        		responseUsuario.getEmail(),
	        		responseUsuario.getPassword(),
	        		responseUsuario.getPhones()
	        );
	 }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
}
