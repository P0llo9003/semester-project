package com.microservicio.fidelizacion_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.fidelizacion_service.dto.FidelizacionDTO;
import com.microservicio.fidelizacion_service.model.Fidelizacion;
import com.microservicio.fidelizacion_service.service.fidelizacionService;

@RestController
@RequestMapping("/fidelizaciones")
public class FidelizacionController {

    private static final Logger logger =
            LoggerFactory.getLogger(FidelizacionController.class);

    private final fidelizacionService fidelizacionService;

    public FidelizacionController(fidelizacionService fidelizacionService) {
        this.fidelizacionService = fidelizacionService;
    }

    @GetMapping
    public ResponseEntity<List<FidelizacionDTO>> listarFidelizaciones() {

        logger.info("GET /fidelizaciones");

        List<Fidelizacion> fidelizaciones = fidelizacionService.findAll();

        List<FidelizacionDTO> dtos = fidelizaciones.stream()
                .map(FidelizacionDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fidelizacion> findById(@PathVariable Long id) {

        logger.info("GET /fidelizaciones/{}", id);

        Fidelizacion fidelizacion = fidelizacionService.findById(id);

        if (fidelizacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(fidelizacion);
    }

    @PostMapping
    public ResponseEntity<FidelizacionDTO> crearFidelizacion(
            @RequestBody FidelizacionDTO fidelizacionDTO) {

        logger.info("POST /fidelizaciones");

        Fidelizacion nueva =
                fidelizacionService.save(fidelizacionDTO.toModel());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(FidelizacionDTO.fromModel(nueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fidelizacion> actualizar(
            @PathVariable Long id,
            @RequestBody Fidelizacion fidelizacion) {

        logger.info("PUT /fidelizaciones/{}", id);

        Fidelizacion actualizada =
                fidelizacionService.update(id, fidelizacion);

        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        logger.info("DELETE /fidelizaciones/{}", id);

        Fidelizacion fidelizacion =
                fidelizacionService.findById(id);

        if (fidelizacion == null) {
            return ResponseEntity.notFound().build();
        }

        fidelizacionService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeFidelizacion(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                fidelizacionService.existById(id));
    }
}