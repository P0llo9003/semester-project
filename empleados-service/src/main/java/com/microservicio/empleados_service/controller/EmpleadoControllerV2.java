package com.microservicio.empleados_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.empleados_service.assemblers.EmpleadoModelAssembler;
import com.microservicio.empleados_service.model.Empleado;
import com.microservicio.empleados_service.service.EmpleadoService;

@RestController
@RequestMapping("empleados/v2")
public class EmpleadoControllerV2 {

    private final EmpleadoService empleadoService;
    private final EmpleadoModelAssembler assembler;

    public EmpleadoControllerV2(
            EmpleadoService empleadoService,
            EmpleadoModelAssembler assembler) {

        this.empleadoService = empleadoService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Empleado>> listarEmpleados() {

        List<EntityModel<Empleado>> empleados =
                empleadoService.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                empleados,
                linkTo(methodOn(EmpleadoControllerV2.class)
                        .listarEmpleados())
                        .withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Empleado> obtenerEmpleado(@PathVariable Long id) {

        Empleado empleado =
                empleadoService.findById(id);

        return assembler.toModel(empleado);
    }
}