package com.microservicio.notificacion_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.notificacion_service.assemblers.NotificacionModelAssembler;
import com.microservicio.notificacion_service.model.Notificacion;
import com.microservicio.notificacion_service.service.NotificacionService;

@RestController
@RequestMapping("notificaciones/v2")
public class NotificacionControllerV2 {

    private final NotificacionService notificacionService;
    private final NotificacionModelAssembler assembler;

    public NotificacionControllerV2(
            NotificacionService notificacionService,
            NotificacionModelAssembler assembler) {

        this.notificacionService = notificacionService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Notificacion>> listarNotificaciones() {

        List<EntityModel<Notificacion>> notificaciones =
                notificacionService.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                notificaciones,
                linkTo(methodOn(NotificacionControllerV2.class)
                        .listarNotificaciones())
                        .withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Notificacion> obtenerNotificacion(@PathVariable Long id) {

        Notificacion notificacion =
                notificacionService.findById(id);

        return assembler.toModel(notificacion);
    }
}