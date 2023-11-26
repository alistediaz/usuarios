package com.mario.usuarios.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.mario.usuarios.classes.ResponseError;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Error en validaciones.")
public class ValidacionException extends Exception {

	private static final long serialVersionUID = -3332242346834265371L;
	private List<ResponseError> error = new ArrayList<>();

	public ValidacionException(List<ResponseError> error){
		super("ValidacionException");
		this.setError(error);
	}

	public List<ResponseError> getError() {
		return error;
	}

	public void setError(List<ResponseError> error) {
		this.error = error;
	}
}

