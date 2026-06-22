package com.microservicio.funciones_service.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.microservicio.funciones_service.controller.EntradaControllerV2;
import com.microservicio.funciones_service.model.Entrada;




@Component
public class EntradaModelAssembler implements RepresentationModelAssembler<Entrada, EntityModel<Entrada>> {

    @Override
    public EntityModel<Entrada> toModel(Entrada entrada) {
        return EntityModel.of(entrada,
                linkTo(methodOn(EntradaControllerV2.class).obtenerEntrada(entrada.getId())).withSelfRel(),
                linkTo(methodOn(EntradaControllerV2.class).listarEntradas()).withRel("entradas"));  
            }
}