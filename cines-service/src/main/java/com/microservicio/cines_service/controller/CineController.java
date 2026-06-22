package com.microservicio.cines_service.controller;

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

import com.microservicio.cines_service.dto.CineDTO;
import com.microservicio.cines_service.model.Cine;
import com.microservicio.cines_service.service.CineService;

@RestController
@RequestMapping("/cines")
public class CineController {

    private final CineService cineService;

    private static final Logger logger =
            LoggerFactory.getLogger(CineController.class);

    public CineController(CineService cineService) {
        this.cineService = cineService;
    }

    @GetMapping
    public ResponseEntity<List<CineDTO>> listarCines() {

        logger.info("GET /cines - Listando cines");

        List<Cine> cines = cineService.findAll();

        List<CineDTO> dtos = cines.stream()
                .map(CineDTO::fromModel)
                .collect(Collectors.toList());

        logger.info("Total de cines encontrados: {}", dtos.size());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cine> findById(@PathVariable Long id) {

        logger.info("GET /cines/{} - Buscando cine", id);

        Cine cine = cineService.findById(id);

        if (cine == null) {
            logger.warn("Cine no encontrado id={}", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("Cine encontrado id={}", id);
        return ResponseEntity.ok(cine);
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<Cine> findByTelefono(@PathVariable int telefono) {

        logger.info("GET /cines/telefono/{} - Buscando cine", telefono);
        Cine cine = cineService.findByTelefono(telefono);

        if (cine == null) {
            logger.warn("Cine no encontrado telefono={}", telefono);
            return ResponseEntity.notFound().build();
        }

        logger.info("Cine encontrado telefono={}", telefono);
        return ResponseEntity.ok(cine);
    }

    @PostMapping
    public ResponseEntity<CineDTO> crearCine(@RequestBody CineDTO CineDto) {

        try {

            logger.info("POST /cines - Creando cine");
            Cine nuevo = cineService.save(CineDto.toModel());
            logger.info("Cine creado exitosamente id={}",
                    nuevo.getId());

            return ResponseEntity.ok(
                    CineDTO.fromModel(nuevo));

        } catch (Exception e) {
            logger.error("Error al crear cine: {}",
                    e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cine> actualizar(@PathVariable Long id, @RequestBody Cine Cine) {

        logger.info("PUT /cines/{} - Actualizando cine", id);
        try {
            Cine cine = cineService.update(id, Cine);
            if (cine == null) {
                logger.warn("Cine no encontrado id={}", id);
                return ResponseEntity.notFound().build();
            }

            logger.info("Cine actualizado exitosamente id={}", id);
            return ResponseEntity.ok(cine);
        } catch (Exception e) {
            logger.error("Error al actualizar cine id={}: {}",
                    id, e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {

        logger.info("DELETE /cines/{} - Eliminando cine", id);

        try {
            Cine cine = cineService.findById(id);
            if (cine == null) {
                logger.warn("Cine no encontrado id={}", id);
                return ResponseEntity.notFound().build();
            }

            cineService.delete(id);
            logger.info("Cine eliminado exitosamente id={}", id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            logger.error("Error al eliminar cine id={}: {}",
                    id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeCine(@PathVariable Long id) {

        logger.info("GET /cines/{}/exists", id);
        return ResponseEntity.ok(
                cineService.existById(id));
    }
}