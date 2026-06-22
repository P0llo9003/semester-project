package com.microservicio.salas_service.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.microservicio.salas_service.controller.SalasControllerV2;
import com.microservicio.salas_service.model.Sala;


@Component
public class SalasModelAssembler implements RepresentationModelAssembler<Sala, EntityModel<Sala>> {

    @Override
    public EntityModel<Sala> toModel(Sala sala) {
        return EntityModel.of(sala,
                linkTo(methodOn(SalasControllerV2.class).obtenerSala(sala.getId())).withSelfRel(),
                linkTo(methodOn(SalasControllerV2.class).listarSalas()).withRel("salas"));  
            }
}