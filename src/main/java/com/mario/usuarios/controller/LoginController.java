package com.mario.usuarios.controller;

import com.mario.usuarios.classes.Error;
import com.mario.usuarios.classes.ErrorStruct;
import com.mario.usuarios.classes.ResponseLogin;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	UsuarioServiceImpl usuarioService;
	@GetMapping
    public ResponseLogin login() throws ValidacionException {
		try{
			return usuarioService.usuarioLogin();
			}
		catch(Exception ex) {
			List<ErrorStruct> errores = new ArrayList<>();
			ErrorStruct error = new ErrorStruct(6, ex.getMessage());
			errores.add(error);
			
			throw new ValidacionException(errores);
		}
	}



	@ExceptionHandler(ValidacionException.class)
	public @ResponseBody Error handleValidacionException(HttpServletRequest request, Exception ex){
		Error error = new Error();
		error.setError(((ValidacionException)ex).getError());
		return error;
	}
}