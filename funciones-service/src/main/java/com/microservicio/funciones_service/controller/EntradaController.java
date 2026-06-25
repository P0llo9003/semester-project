package com.microservicio.funciones_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.funciones_service.dto.EntradaDTO;
import com.microservicio.funciones_service.model.Entrada;
import com.microservicio.funciones_service.service.EntradaService;

@RestController
@RequestMapping("/entradas")
public class EntradaController {

    private static final Logger logger = LoggerFactory.getLogger(EntradaController.class);

    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }


    @GetMapping
    public ResponseEntity<List<EntradaDTO>> findAll() {

        logger.info("GET /entradas");

        List<Entrada> entradas = entradaService.findAll();

        logger.debug("Cantidad de entradas obtenidas: {}", entradas.size());

        List<EntradaDTO> dtos = entradas.stream()
                .map(EntradaDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntradaDTO> findById(@PathVariable Long id) {

        logger.info("GET /entradas/{}", id);

        Entrada entrada = entradaService.findById(id);

        if (entrada == null) {

            logger.warn("Entrada no encontrada id={}", id);

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                EntradaDTO.fromModel(entrada));
    }


    @PostMapping
    public ResponseEntity<EntradaDTO> save(
            @RequestBody EntradaDTO entradaDTO) {

        logger.info("POST /entradas - funcionId={}, usuarioId={}",
                entradaDTO.getFuncionId(),
                entradaDTO.getUsuarioId());

        Entrada savedEntrada =
                entradaService.save(entradaDTO.toModel());

        logger.info(
                "Entrada creada correctamente id={}",
                savedEntrada.getId());

        return ResponseEntity.ok(
                EntradaDTO.fromModel(savedEntrada));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntradaDTO> update(@PathVariable Long id, @RequestBody EntradaDTO entradaDTO) {

        logger.info("PUT /entradas/{}", id);

        Entrada updatedEntrada = entradaService.update(id, entradaDTO.toModel());

        if (updatedEntrada == null) {

            logger.warn("No se pudo actualizar entrada id={}", id);

            return ResponseEntity.notFound().build();
        }

        logger.info("Entrada actualizada correctamente id={}", updatedEntrada.getId());

        return ResponseEntity.ok(EntradaDTO.fromModel(updatedEntrada));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        logger.info("DELETE /entradas/{}", id);

        Entrada entrada = entradaService.findById(id);

        if (entrada == null) {

            logger.warn("Entrada no encontrada id={}", id);

            return ResponseEntity.notFound().build();
        }

        entradaService.delete(id);

        logger.info("Entrada eliminada correctamente id={}", id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalEntradas() {

    logger.info("GET /entradas/total");

    return ResponseEntity.ok(entradaService.totalEntradas());
}



    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {

        logger.info("GET /entradas/{}/exists", id);

        return ResponseEntity.ok(entradaService.existsById(id));
    }


}