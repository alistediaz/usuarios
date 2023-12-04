package com.mario.usuarios.service;

import com.mario.usuarios.classes.ErrorStruct;
import com.mario.usuarios.classes.ResponseLogin;
import com.mario.usuarios.classes.ResponseSignUp;
import com.mario.usuarios.exceptions.ValidacionException;
import com.mario.usuarios.model.Usuario;
import com.mario.usuarios.repository.UsuarioRepository;
import com.mario.usuarios.utils.JwtTokenUtil;
import com.mario.usuarios.utils.ValidaEmailPassword;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public Optional<Usuario> findByName(String username) {
        return  usuarioRepository.findByName(username);
    }

    public ResponseLogin usuarioLogin() throws ValidacionException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Usuario> usuario = this.findByName( authentication.getName());
        List<ErrorStruct> errores = new ArrayList<>();


        if (!usuario.isPresent()) {
            ErrorStruct error = new ErrorStruct(4, "Usuario no existe.");
            errores.add(error);
            throw new ValidacionException(errores);
        }

        return ResponseLogin.build(usuario, jwtTokenUtil.generateToken(UsuarioDetailsImpl.build(usuario.get())));
    }

    public ResponseSignUp usuarioSignUp(Usuario usuario) throws ValidacionException {
        List<ErrorStruct> errores = new ArrayList<>(ValidaEmailPassword.validaEmailPassword(usuario));
        Optional<Usuario> usuarioExiste =  this.findByName(usuario.getName());

        if(usuarioExiste.isPresent()) {
            ErrorStruct error = new ErrorStruct(3, "Usuario ya existe: " + usuario.getName());
            errores.add(error);
        }

        if (errores.size()>0) {
            throw new ValidacionException(errores);
        }

        usuario.setCreated(new Date());
        usuario.setLastLogin(new Date());
        usuario.setActive(true);
        ResponseSignUp responseSignUp = new ResponseSignUp(this.save(usuario));
        responseSignUp.setToken(jwtTokenUtil.generateToken(UsuarioDetailsImpl.build(usuario)));

        return responseSignUp;
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}