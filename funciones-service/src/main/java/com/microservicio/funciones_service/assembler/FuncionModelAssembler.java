package com.microservicio.funciones_service.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.microservicio.funciones_service.controller.FuncionControllerV2;
import com.microservicio.funciones_service.model.Funcion;


@Component
public class FuncionModelAssembler implements RepresentationModelAssembler<Funcion, EntityModel<Funcion>> {

    @Override
    public EntityModel<Funcion> toModel(Funcion funcion) {
        return EntityModel.of(funcion,
                linkTo(methodOn(FuncionControllerV2.class).obtenerFuncion(funcion.getId())).withSelfRel(),
                linkTo(methodOn(FuncionControllerV2.class).listarFunciones()).withRel("funciones"));  
            }
}