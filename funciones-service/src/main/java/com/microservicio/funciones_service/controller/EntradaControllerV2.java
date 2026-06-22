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

import com.microservicio.funciones_service.assembler.EntradaModelAssembler;
import com.microservicio.funciones_service.model.Entrada;
import com.microservicio.funciones_service.service.EntradaService;





@RestController
@RequestMapping("entradas/v2")
public class EntradaControllerV2 {
    private final EntradaService entradaService;
    private final EntradaModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(EntradaControllerV2.class);

    public EntradaControllerV2(EntradaService entradaService, EntradaModelAssembler assembler) {
        this.entradaService = entradaService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Entrada>> listarEntradas() {
        logger.info("V2 GET /entradas - Listando entradas");
        List<EntityModel<Entrada>> entrada = entradaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(entrada, linkTo(methodOn(EntradaControllerV2.class).listarEntradas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Entrada> obtenerEntrada(@PathVariable Long id) {
        logger.info("V2 GET /entradas/{} - Obteniendo entrada", id);
        Entrada entrada = entradaService.findById(id);
        return assembler.toModel(entrada);
    }
}
