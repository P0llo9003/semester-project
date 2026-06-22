package com.microservicio.salas_service.controller;

import java.util.List;

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

import com.microservicio.salas_service.model.Sala;
import com.microservicio.salas_service.service.SalasService;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private static final Logger logger = LoggerFactory.getLogger(SalaController.class);

    private final SalasService salasService;

    public SalaController(SalasService salasService) {
        this.salasService = salasService;
    }

    @GetMapping
    public ResponseEntity<List<Sala>> findAll() {

        logger.info("GET /salas");

        List<Sala> salas = salasService.findAll();

        logger.debug("Cantidad de salas obtenidas: {}", salas.size());
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> findById(@PathVariable Long id) {

        logger.info("GET /salas/{}", id);

        Sala sala = salasService.findById(id);

        if (sala != null) {
            return ResponseEntity.ok(sala);
        }

        logger.warn("Sala no encontrada id={}", id);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Sala>> findByTipo(@PathVariable String tipo) {

        logger.info("GET /salas/tipo/{}", tipo);

        return ResponseEntity.ok(salasService.findByTipo(tipo));
    }

    @GetMapping("/cine/{cineId}")
    public ResponseEntity<List<Sala>> findByCine(@PathVariable Long cineId) {

        logger.info("GET /salas/cine/{}", cineId);

        return ResponseEntity.ok(salasService.findByCine(cineId));
    }

    @PostMapping
    public ResponseEntity<Sala> save(@RequestBody Sala sala) {

        logger.info("POST /salas - nombre={}, cineId={}", sala.getNombre(), sala.getCineId());

        Sala salaGuardada = salasService.save(sala);

        logger.info("Sala creada exitosamente id={}", salaGuardada.getId());

        return ResponseEntity.ok(salaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> update(@PathVariable Long id, @RequestBody Sala salaActualizada) {

        logger.info("PUT /salas/{}", id);

        Sala sala = salasService.update(id, salaActualizada);

        if (sala != null) {
            logger.info("Sala actualizada exitosamente id={}", id);
            return ResponseEntity.ok(sala);
        }

        logger.warn("No se pudo actualizar sala id={}", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        logger.info("DELETE /salas/{}", id);

        Sala sala = salasService.findById(id);

        if (sala != null) {
            salasService.delete(id);
            logger.info("Sala eliminada exitosamente id={}", id);
            return ResponseEntity.noContent().build();
        }

        logger.warn("Sala no encontrada id={}", id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeSala(@PathVariable Long id) {

        logger.info("GET /salas/{}/exists", id);

        return ResponseEntity.ok(salasService.existById(id));
    }
}