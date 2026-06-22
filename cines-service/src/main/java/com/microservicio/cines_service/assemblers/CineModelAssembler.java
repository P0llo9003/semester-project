package com.microservicio.cines_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.cines_service.controller.CineControllerV2;
import com.microservicio.cines_service.model.Cine;

@Component
public class CineModelAssembler implements RepresentationModelAssembler<Cine, EntityModel<Cine>> {

    @Override
    public EntityModel<Cine> toModel(Cine cine) {
        return EntityModel.of(cine,
                linkTo(methodOn(CineControllerV2.class).obtenerCine(cine.getId())).withSelfRel(),
                linkTo(methodOn(CineControllerV2.class).listarCines()).withRel("cines")); 
            }
    
}
