package com.mario.usuarios.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mario.usuarios.utils.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtRequestFilter jwtTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtTokenFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        	.csrf()
        	.disable()
        	.authorizeRequests().antMatchers("/actuator/**", "/sign-up/**", "/h2-console/**").permitAll()
        	.and()
        	.authorizeRequests()
        	.anyRequest()
        	.authenticated()
        	.and()
        	.exceptionHandling()
        	.authenticationEntryPoint((request, response, authException) -> {
	            Map<String, Object> responseMap = new HashMap<>();
	            ObjectMapper mapper = new ObjectMapper();
	            response.setStatus(401);
	            responseMap.put("error", true);
	            responseMap.put("message", "Unauthorized");
	            response.setHeader("content-type", "application/json");
	            String responseMsg = mapper.writeValueAsString(responseMap);
	            response.getWriter().write(responseMsg);
	        })
        	.and()
        	.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and()
        	.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
