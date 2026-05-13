package com.microservicio.usuarios_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservicio.usuarios_service.model.Usuario;
import com.microservicio.usuarios_service.repository.UsuarioRepository;



@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService (UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;

    }

    public Usuario save (Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public boolean findById (long id){
        return usuarioRepository.existsById(id);
    }
    
    
}
