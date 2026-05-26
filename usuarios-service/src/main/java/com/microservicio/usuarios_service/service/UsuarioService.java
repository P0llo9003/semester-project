package com.microservicio.usuarios_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservicio.usuarios_service.exception.ResourceNotFoundException;
import com.microservicio.usuarios_service.model.Usuario;
import com.microservicio.usuarios_service.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private static final Logger logger =
            LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public List<Usuario> findAll() {

        logger.info("Listando todos los usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        logger.debug("Cantidad de usuarios encontrados: {}",
                usuarios.size());
        return usuarios;
    }


    public Usuario findById(Long id) {

        logger.info("Buscando usuario con id={}", id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado id={}", id);
                    return new ResourceNotFoundException(
                            "Usuario no encontrado");
                });
    }


    public Usuario save(Usuario usuario) {

        logger.info("Guardando usuario RUN={}-{}",
        usuario.getRun(),
        usuario.getDv());

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        logger.info("Usuario guardado exitosamente id={}",
                usuarioGuardado.getId());

        return usuarioGuardado;
    }


    public Usuario update(Long id, Usuario usuarioActualizado) {

        logger.info("Actualizando usuario id={}", id);
        Usuario usuarioExistente = findById(id);

        usuarioExistente.setRun(usuarioActualizado.getRun());
        usuarioExistente.setDv(usuarioActualizado.getDv());
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellido(usuarioActualizado.getApellido());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());

        Usuario usuarioGuardado =
                usuarioRepository.save(usuarioExistente);
        logger.info("Usuario actualizado exitosamente id={}", id);
        return usuarioGuardado;
    }


    public void delete(Long id) {

        logger.info("Eliminando usuario id={}", id);
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
        logger.info("Usuario eliminado exitosamente id={}", id);
    }


    public boolean existePorId(Long id) {
		return usuarioRepository.existsById(id);
	}

}
