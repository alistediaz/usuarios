package com.mario.usuarios.classes;

import java.time.LocalDateTime;

public class ResponseError {

	LocalDateTime timestamp;
	int codigo;
	String detail;
	
	public ResponseError() {
	}

	
	public ResponseError(int codigo, String detail) {
		this.timestamp = LocalDateTime.now();
		this.codigo = codigo;
		this.detail = detail;
	}


	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
