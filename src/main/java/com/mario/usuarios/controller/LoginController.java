package com.mario.usuarios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mario.usuarios.classes.ResponseObject;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
	
	@GetMapping
	public ResponseObject login() {
		
	    return null;
	}
}
