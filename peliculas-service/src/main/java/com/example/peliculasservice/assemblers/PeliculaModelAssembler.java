package com.example.peliculasservice.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.peliculasservice.controller.PeliculaControllerV2;
import com.example.peliculasservice.model.Pelicula;

@Component
public class PeliculaModelAssembler implements RepresentationModelAssembler<Pelicula, EntityModel<Pelicula>> {

    @Override
    public EntityModel<Pelicula> toModel(Pelicula pelicula) {
        return EntityModel.of(pelicula,
                linkTo(methodOn(PeliculaControllerV2.class).obtenerPelicula(pelicula.getId())).withSelfRel(),
                linkTo(methodOn(PeliculaControllerV2.class).listarPeliculas()).withRel("peliculas"));  
            }
}