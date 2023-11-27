package com.mario.usuarios.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mario.usuarios.classes.ErrorStruct;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	/*@ExceptionHandler(ValidacionException.class)
	public @ResponseBody ResponseError handleValidacionException(HttpServletRequest request, Exception ex){
		
		ResponseError response = new ResponseError(404, "no existe");

		return response;
	}*/
}
