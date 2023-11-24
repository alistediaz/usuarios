package com.mario.usuarios.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mario.usuarios.model.Usuario;

@RestController
public class SignUpController {

    @PostMapping("/sign-up")
    public String signUp(@RequestBody Usuario usuario) {
		return null;
        
    }
}
