package com.microservicio.confiteria_service.controller;

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

import com.microservicio.confiteria_service.assembler.ConfiteriaModelAssembler;
import com.microservicio.confiteria_service.model.Confiteria;
import com.microservicio.confiteria_service.service.ConfiteriaService;


@RestController
@RequestMapping("Confiteria/v2")
public class ConfiteriaControllerV2 {

    private final ConfiteriaService confiteriaService;
    private final ConfiteriaModelAssembler assembler;
    
    private static final Logger logger = LoggerFactory.getLogger(ConfiteriaControllerV2.class);

    public ConfiteriaControllerV2(ConfiteriaService confiteriaService, ConfiteriaModelAssembler assembler) {
        this.confiteriaService = confiteriaService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Confiteria>> listarProductos() {
        logger.info("V2 GET /confiteria - listando productos");
        List<EntityModel<Confiteria>> confi = confiteriaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(confi, linkTo(methodOn(ConfiteriaControllerV2.class).listarProductos()).withSelfRel());
    }


    @GetMapping("/id")
    public EntityModel<Confiteria> obtenerProducto(@PathVariable Long id) {
        logger.info("V2 GET /confiteria/{} - Obteniendo Producto", id);
        Confiteria confi = confiteriaService.findById(id);
        return assembler.toModel(confi);
    }
}
