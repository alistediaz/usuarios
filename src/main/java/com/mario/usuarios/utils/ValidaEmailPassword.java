package com.mario.usuarios.utils;

import java.util.ArrayList;
import java.util.List;

import com.mario.usuarios.classes.ResponseError;
import com.mario.usuarios.model.Usuario;

public abstract class ValidaEmailPassword {

	public static List<ResponseError> validaEmailPassword(Usuario usuario) {
		List<ResponseError> errores = new ArrayList<>();
    	
		if(!RegExUtils.validateEmailFormat(usuario.getEmail())) {
    		ResponseError error = new ResponseError(1, "Formato de email no válido");
    		errores.add(error);
    	}
    	
    	if(!RegExUtils.validatePasswordRules(usuario.getPassword())) {
    		ResponseError error = new ResponseError(2, "La contraseña no cumple con las reglas");
    		errores.add(error);
    	}
    	
    	return errores;
	}

}
