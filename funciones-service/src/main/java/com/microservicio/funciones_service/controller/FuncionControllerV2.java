package com.microservicio.funciones_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.funciones_service.assembler.FuncionModelAssembler;
import com.microservicio.funciones_service.model.Funcion;
import com.microservicio.funciones_service.service.FuncionService;



@RestController
@RequestMapping("funciones/v2")
public class FuncionControllerV2 {
    private final FuncionService funcionService;
    private final FuncionModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(FuncionControllerV2.class);

    public FuncionControllerV2(FuncionService funcionService, FuncionModelAssembler assembler) {
        this.funcionService = funcionService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Funcion>> listarFunciones() {
        logger.info("V2 GET /funciones - Listando funciones");
        List<EntityModel<Funcion>> funcion = funcionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(funcion, linkTo(methodOn(FuncionControllerV2.class).listarFunciones()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Funcion> obtenerFuncion(@PathVariable Long id) {
        logger.info("V2 GET /funciones/{} - Obteniendo funcion", id);
        Funcion funcion = funcionService.findById(id);
        return assembler.toModel(funcion);
    }
}
