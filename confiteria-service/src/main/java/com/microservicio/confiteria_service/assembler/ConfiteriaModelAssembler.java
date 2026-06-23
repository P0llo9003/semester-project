package com.microservicio.confiteria_service.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.microservicio.confiteria_service.controller.ConfiteriaControllerV2;
import com.microservicio.confiteria_service.model.Confiteria;



@Component
public class ConfiteriaModelAssembler implements RepresentationModelAssembler<Confiteria, EntityModel<Confiteria>> {

    @Override
    public EntityModel<Confiteria> toModel(Confiteria confiteria) {
        return EntityModel.of(confiteria,
                linkTo(methodOn(ConfiteriaControllerV2.class).obtenerProducto(confiteria.getId())).withSelfRel(),
                linkTo(methodOn(ConfiteriaControllerV2.class).listarProductos()).withRel("confiteria"));  
            }
}