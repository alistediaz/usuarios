package com.mario.usuarios.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mario.usuarios.classes.ResponseError;
import com.mario.usuarios.classes.ResponseSignUp;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.repository.UsuarioRepository;
import com.mario.usuarios.utils.ValidaEmailPassword;

@RestController
public class SignUpController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
    @PostMapping("/sign-up")
    public ResponseSignUp signUp(@RequestBody Usuario usuario) throws ValidacionException {
    	ResponseSignUp responseSignUp = new ResponseSignUp();
    	List<ResponseError> errores = new ArrayList<>(ValidaEmailPassword.validaEmailPassword(usuario));
    	Optional<Usuario> usuarioExiste =  usuarioRepository.findByUsername(usuario.getUsername());
    	
    	if(!usuarioExiste.isEmpty()) {
    		ResponseError error = new ResponseError(3, "Usuario ya existe.");
    		errores.add(error);
    	}
    	
    	if (errores.size()>0) {
    		throw new ValidacionException(errores);
    	}
    	
		return responseSignUp;
        
    }
    
	@ExceptionHandler(ValidacionException.class)
	public @ResponseBody List<ResponseError> handleValidacionException(HttpServletRequest request, Exception ex){
		return ((ValidacionException)ex).getError();
	}
}
