package com.mario.usuarios.exceptions;

import com.mario.usuarios.classes.ErrorStruct;

import java.util.ArrayList;
import java.util.List;

public class ValidacionException extends Exception {

	private static final long serialVersionUID = -3332242346834265371L;
	private List<ErrorStruct> error = new ArrayList<>();

	public ValidacionException(List<ErrorStruct> error){
		super(error.get(0).getDetail());
		this.setError(error);
	}

	public List<ErrorStruct> getError() {
		return error;
	}

	public void setError(List<ErrorStruct> error) {
		this.error = error;
	}
}

