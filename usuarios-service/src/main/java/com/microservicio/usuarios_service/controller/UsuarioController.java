package com.microservicio.usuarios_service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.usuarios_service.model.Usuario;
import com.microservicio.usuarios_service.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger logger =
            LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        logger.info("GET /usuarios");

        List<Usuario> usuarios = usuarioService.findAll();

        logger.debug("Cantidad de usuarios obtenidos: {}",
                usuarios.size());

        return ResponseEntity.ok(usuarios);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {

        logger.info("GET /usuarios/{}", id);

        Usuario usuario = usuarioService.findById(id);

        logger.info("Usuario encontrado con id={}", id);

        return ResponseEntity.ok(usuario);
    }

    
    @PostMapping
    public ResponseEntity<Usuario> agregar(
            @RequestBody Usuario usuario) {

        logger.info("POST /usuarios - correo={}",
                usuario.getCorreo());

        Usuario nuevoUsuario =
                usuarioService.save(usuario);

        logger.info("Usuario creado exitosamente id={}",
                nuevoUsuario.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoUsuario);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        logger.info("PUT /usuarios/{}", id);

        Usuario usuarioActualizado =
                usuarioService.update(id, usuario);

        logger.info("Usuario actualizado id={}", id);

        return ResponseEntity.ok(usuarioActualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        logger.info("DELETE /usuarios/{}", id);

        usuarioService.delete(id);

        logger.info("Usuario eliminado id={}", id);

        return ResponseEntity.noContent().build();
    }
}