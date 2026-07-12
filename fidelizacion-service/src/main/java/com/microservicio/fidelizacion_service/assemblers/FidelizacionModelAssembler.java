package com.microservicio.fidelizacion_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.fidelizacion_service.controller.FidelizacionControllerV2;
import com.microservicio.fidelizacion_service.model.Fidelizacion;

@Component
public class FidelizacionModelAssembler
        implements RepresentationModelAssembler<Fidelizacion, EntityModel<Fidelizacion>> {

    @Override
    public EntityModel<Fidelizacion> toModel(Fidelizacion fidelizacion) {

        return EntityModel.of(
                fidelizacion,
                linkTo(methodOn(FidelizacionControllerV2.class)
                        .obtenerFidelizacion(fidelizacion.getId()))
                        .withSelfRel(),
                linkTo(methodOn(FidelizacionControllerV2.class)
                        .listarFidelizaciones())
                        .withRel("fidelizaciones"));
    }
}