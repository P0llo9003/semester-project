package com.microservicio.cines_service.controller;

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

import com.microservicio.cines_service.assemblers.CineModelAssembler;
import com.microservicio.cines_service.model.Cine;
import com.microservicio.cines_service.service.CineService;

@RestController
@RequestMapping("cines/v2")
public class CineControllerV2 {
    private final CineService cineService;
    private final CineModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(CineControllerV2.class);

    public CineControllerV2(CineService cineService, CineModelAssembler assembler) {
        this.cineService = cineService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Cine>> listarCines() {
        logger.info("V2 GET /cines - Listando cines");
        List<EntityModel<Cine>> cines = cineService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(cines, linkTo(methodOn(CineControllerV2.class).listarCines()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Cine> obtenerCine(@PathVariable Long id) {
        logger.info("V2 GET /cines/{} - obteniendo cine", id);
        Cine cine = cineService.findById(id);
        return assembler.toModel(cine);

    }

    
}
