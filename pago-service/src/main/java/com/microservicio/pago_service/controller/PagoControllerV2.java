package com.microservicio.pago_service.controller;

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

import com.microservicio.pago_service.assembler.PagoModelAssembler;
import com.microservicio.pago_service.model.Pago;
import com.microservicio.pago_service.service.PagoService;

@RestController
@RequestMapping("pagos/v2")
public class PagoControllerV2 {
    private final PagoService pagoService;
    private final PagoModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(PagoControllerV2.class);

    public PagoControllerV2(PagoService pagoService, PagoModelAssembler assembler) {
        this.pagoService = pagoService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Pago>> listarPagos() {
        logger.info("V2 GET /pagos - Listando pagos");
        List<EntityModel<Pago>> pagos = pagoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pagos, linkTo(methodOn(PagoControllerV2.class).listarPagos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Pago> obtenerPago(@PathVariable Long id) {
        logger.info("V2 GET /pagos/{} - Obteniendo pago", id);
        Pago pago = pagoService.findById(id);
        return assembler.toModel(pago);
    }
}
