package com.mario.usuarios.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mario.usuarios.classes.Error;
import com.mario.usuarios.classes.ErrorStruct;
import com.mario.usuarios.classes.ResponseLogin;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.repository.UsuarioRepository;
import com.mario.usuarios.service.UsuarioDetailsImpl;
import com.mario.usuarios.utils.JwtTokenUtil;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@GetMapping
    public ResponseLogin login() throws ValidacionException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Usuario> usuario = usuarioRepository.findByName( authentication.getName());
    	List<ErrorStruct> errores = new ArrayList<>();

        
        if (usuario.isEmpty()) {
    		ErrorStruct error = new ErrorStruct(4, "Usuario no existe.");
    		errores.add(error);
    		throw new ValidacionException(errores);
    	}

        return ResponseLogin.build(usuario, jwtTokenUtil.generateToken(UsuarioDetailsImpl.build(usuario.get())));
    }
	
	@ExceptionHandler(ValidacionException.class)
	public @ResponseBody Error handleValidacionException(HttpServletRequest request, Exception ex){
		Error error = new Error();
		error.setError(((ValidacionException)ex).getError());
		return error;
	}
}
