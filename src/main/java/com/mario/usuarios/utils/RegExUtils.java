package com.mario.usuarios.utils;

import java.util.regex.Pattern;

public abstract class RegExUtils {

	public static boolean validateEmailFormat(String emailAddress) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		
	    return Pattern.compile(regexPattern)
	    	      .matcher(emailAddress)
	    	      .matches();
	}
	
	public static boolean validatePasswordRules(String password) {
		String regexPattern = "^(?=.*[A-Z])(?!.*[A-Z].*[A-Z])(?=.*\\d.*\\d)(?!.*\\d.*\\d.*\\d)(?!.*[^a-zA-Z0-9]).{8,12}$";
		
	    return Pattern.compile(regexPattern)
	    	      .matcher(password)
	    	      .matches();
	}
}
