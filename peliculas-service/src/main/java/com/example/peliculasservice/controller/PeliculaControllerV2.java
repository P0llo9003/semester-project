package com.example.peliculasservice.controller;

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

import com.example.peliculasservice.assemblers.PeliculaModelAssembler;
import com.example.peliculasservice.model.Pelicula;
import com.example.peliculasservice.service.PeliculaService;

@RestController
@RequestMapping("peliculas/v2")
public class PeliculaControllerV2 {
    private final PeliculaService peliculaService;
    private final PeliculaModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(PeliculaControllerV2.class);

    public PeliculaControllerV2(PeliculaService peliculaService, PeliculaModelAssembler assembler) {
        this.peliculaService = peliculaService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Pelicula>> listarPeliculas() {
        logger.info("V2 GET /pelicula - Listando peliculas");
        List<EntityModel<Pelicula>> peliculas = peliculaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(peliculas, linkTo(methodOn(PeliculaControllerV2.class).listarPeliculas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Pelicula> obtenerPelicula(@PathVariable Long id) {
        logger.info("V2 GET /peliculas/{} - Obteniendo pelicula", id);
        Pelicula pelicula = peliculaService.findById(id);
        return assembler.toModel(pelicula);
    }
}
