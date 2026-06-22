package com.microservicio.salas_service.controller;

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

import com.microservicio.salas_service.assembler.SalasModelAssembler;
import com.microservicio.salas_service.model.Sala;
import com.microservicio.salas_service.service.SalasService;

@RestController
@RequestMapping("salas/v2")
public class SalasControllerV2 {
    private final SalasService salasService;
    private final SalasModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(SalasControllerV2.class);

    public SalasControllerV2(SalasService salasService, SalasModelAssembler assembler) {
        this.salasService = salasService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Sala>> listarSalas() {
        logger.info("V2 GET /salas - Listando salas");
        List<EntityModel<Sala>> salas = salasService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(salas, linkTo(methodOn(SalasControllerV2.class).listarSalas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Sala> obtenerSala(@PathVariable Long id) {
        logger.info("V2 GET /salas/{} - Obteniendo sala", id);
        Sala sala = salasService.findById(id);
        return assembler.toModel(sala);
    }
}
