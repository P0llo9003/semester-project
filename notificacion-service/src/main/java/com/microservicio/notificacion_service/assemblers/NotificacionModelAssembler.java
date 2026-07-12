package com.microservicio.notificacion_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.notificacion_service.controller.NotificacionControllerV2;
import com.microservicio.notificacion_service.model.Notificacion;

@Component
public class NotificacionModelAssembler
        implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {

    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {

        return EntityModel.of(
                notificacion,
                linkTo(methodOn(NotificacionControllerV2.class)
                        .obtenerNotificacion(notificacion.getId()))
                        .withSelfRel(),
                linkTo(methodOn(NotificacionControllerV2.class)
                        .listarNotificaciones())
                        .withRel("notificaciones"));
    }
}