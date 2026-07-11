package com.microservicio.fidelizacion_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.fidelizacion_service.assemblers.FidelizacionModelAssembler;
import com.microservicio.fidelizacion_service.model.Fidelizacion;
import com.microservicio.fidelizacion_service.service.fidelizacionService;

@RestController
@RequestMapping("fidelizaciones/v2")
public class FidelizacionControllerV2 {

    private final fidelizacionService fidelizacionService;
    private final FidelizacionModelAssembler assembler;

    public FidelizacionControllerV2(
            fidelizacionService fidelizacionService,
            FidelizacionModelAssembler assembler) {

        this.fidelizacionService = fidelizacionService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Fidelizacion>> listarFidelizaciones() {

        List<EntityModel<Fidelizacion>> fidelizaciones =
                fidelizacionService.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                fidelizaciones,
                linkTo(methodOn(FidelizacionControllerV2.class)
                        .listarFidelizaciones())
                        .withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Fidelizacion> obtenerFidelizacion(@PathVariable Long id) {

        Fidelizacion fidelizacion =
                fidelizacionService.findById(id);

        return assembler.toModel(fidelizacion);
    }
}
