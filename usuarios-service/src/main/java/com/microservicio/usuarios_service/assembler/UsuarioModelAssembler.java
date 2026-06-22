package com.microservicio.usuarios_service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.usuarios_service.model.Usuario;
import com.microservicio.usuarios_service.controller.UsuarioControllerV2;


@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuario(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("usuarios"));  
            }
}