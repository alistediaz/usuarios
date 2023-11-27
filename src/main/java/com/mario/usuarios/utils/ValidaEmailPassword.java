package com.mario.usuarios.utils;

import java.util.ArrayList;
import java.util.List;

import com.mario.usuarios.classes.ErrorStruct;
import com.mario.usuarios.model.Usuario;

public abstract class ValidaEmailPassword {

	public static List<ErrorStruct> validaEmailPassword(Usuario usuario) {
		List<ErrorStruct> errores = new ArrayList<>();
		
		if(!RegExUtils.validateEmailFormat(usuario.getEmail())) {
    		ErrorStruct emailError = new ErrorStruct(1, "Formato de email no válido");
    		errores.add(emailError);
    	}
    	
    	if(!RegExUtils.validatePasswordRules(usuario.getPassword())) {
    		ErrorStruct passwordError = new ErrorStruct(2, "La contraseña no cumple con las reglas");
    		errores.add(passwordError);
    	}
    	
    	return errores;
	}

}
