package com.microservicio.salas_service.controller;

import java.util.List;

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

    private final SalasService salasService;

    public SalaController(SalasService salasService) {
        this.salasService = salasService;
    }


    // GET ALL
    @GetMapping
    public ResponseEntity<List<Sala>> findAll() {

        return ResponseEntity.ok(
                salasService.findAll());
    }


    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Sala> findById(
            @PathVariable Long id) {

        Sala sala = salasService.findById(id);

        if (sala != null) {
            return ResponseEntity.ok(sala);
        }

        return ResponseEntity.notFound().build();
    }


    // GET BY TIPO
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Sala>> findByTipo(
            @PathVariable String tipo) {

        return ResponseEntity.ok(
                salasService.findByTipo(tipo));
    }


    // GET BY CINE
    @GetMapping("/cine/{cineId}")
    public ResponseEntity<List<Sala>> findByCine(
            @PathVariable Long cineId) {

        return ResponseEntity.ok(
                salasService.findByCine(cineId));
    }


    // POST
    @PostMapping
    public ResponseEntity<Sala> save(
            @RequestBody Sala sala) {

        return ResponseEntity.ok(
                salasService.save(sala));
    }


    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Sala> update(
            @PathVariable Long id,
            @RequestBody Sala salaActualizada) {

        Sala sala = salasService.update(id, salaActualizada);

        if (sala != null) {
            return ResponseEntity.ok(sala);
        }

        return ResponseEntity.notFound().build();
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        Sala sala = salasService.findById(id);

        if (sala != null) {

            salasService.delete(id);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeSala(@PathVariable Long id) {
        return ResponseEntity.ok(salasService.existById(id));
    }

    
}