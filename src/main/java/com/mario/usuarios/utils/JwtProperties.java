package com.mario.usuarios.utils;

import javax.crypto.SecretKey;

public class JwtProperties {

	private SecretKey secret;
    private String issuer = "marioaliste";
    private Long expireMinutes = 1 * 60 * 1000L;

	public JwtProperties(SecretKey secretKeyFor) {
		this.secret = secretKeyFor;
	}

	public SecretKey getSecret() {
		return secret;
	}

	public void setSecret(SecretKey secret) {
		this.secret = secret;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Long getExpireMinutes() {
		return expireMinutes;
	}

	public void setExpireMinutes(Long expireMinutes) {
		this.expireMinutes = expireMinutes;
	}
    
    
}
