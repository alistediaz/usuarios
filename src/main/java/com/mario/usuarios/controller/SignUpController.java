package com.mario.usuarios.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mario.usuarios.classes.Error;
import com.mario.usuarios.classes.ErrorStruct;
import com.mario.usuarios.classes.ResponseSignUp;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.repository.UsuarioRepository;
import com.mario.usuarios.service.UsuarioDetailsImpl;
import com.mario.usuarios.utils.JwtTokenUtil;
import com.mario.usuarios.utils.ValidaEmailPassword;

@RestController
public class SignUpController {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
    @PostMapping("/sign-up")
    public ResponseSignUp signUp(@RequestBody Usuario usuario) throws ValidacionException {
    	List<ErrorStruct> errores = new ArrayList<>(ValidaEmailPassword.validaEmailPassword(usuario));
    	Optional<Usuario> usuarioExiste =  usuarioRepository.findByName(usuario.getName());
    	
    	if(!usuarioExiste.isEmpty()) {
    		ErrorStruct error = new ErrorStruct(3, "Usuario ya existe.");
    		errores.add(error);
    	}
    	
    	if (errores.size()>0) {
    		throw new ValidacionException(errores);
    	}
    	
    	usuario.setCreated(LocalDateTime.now());
    	usuario.setLastLogin(new Date());
    	usuario.setActive(true);

    	ResponseSignUp responseSignUp = new ResponseSignUp(usuarioRepository.save(usuario));
    	responseSignUp.setToken(jwtTokenUtil.generateToken(UsuarioDetailsImpl.build(usuario)));
    	
		return responseSignUp;
        
    }
    
	@ExceptionHandler(ValidacionException.class)
	public @ResponseBody Error handleValidacionException(HttpServletRequest request, Exception ex){
		Error error = new Error();
		error.setError(((ValidacionException)ex).getError());
		return error;
	}
}
