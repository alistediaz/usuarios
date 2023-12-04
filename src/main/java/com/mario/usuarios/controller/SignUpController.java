package com.mario.usuarios.controller;

import javax.servlet.http.HttpServletRequest;

import com.mario.usuarios.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mario.usuarios.classes.Error;
import com.mario.usuarios.classes.ResponseSignUp;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;

@RestController
public class SignUpController {

	@Autowired
	UsuarioServiceImpl usuarioService;
	
    @PostMapping("/sign-up")
    public ResponseSignUp signUp(@RequestBody Usuario usuario) throws ValidacionException {
    	return usuarioService.usuarioSignUp(usuario);
	}


	@ExceptionHandler(ValidacionException.class)
	public @ResponseBody Error handleValidacionException(HttpServletRequest request, Exception ex){
		Error error = new Error();
		error.setError(((ValidacionException)ex).getError());
		return error;
	}
}