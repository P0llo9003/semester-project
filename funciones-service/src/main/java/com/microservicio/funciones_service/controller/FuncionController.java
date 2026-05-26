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

import com.microservicio.funciones_service.dto.FuncionDTO;
import com.microservicio.funciones_service.model.Funcion;
import com.microservicio.funciones_service.service.FuncionService;

@RestController
@RequestMapping("/funciones")
public class FuncionController {

    private static final Logger logger = LoggerFactory.getLogger(FuncionController.class);

    private final FuncionService funcionService;

    public FuncionController(FuncionService funcionService) {
        this.funcionService = funcionService;
    }


    @GetMapping
    public ResponseEntity<List<FuncionDTO>> findAll() {

        logger.info("GET /funciones");

        List<Funcion> funciones = funcionService.findAll();

        logger.debug("Cantidad de funciones obtenidas: {}", funciones.size());

        List<FuncionDTO> dtos = funciones.stream()
                .map(FuncionDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FuncionDTO> findById(@PathVariable Long id) {

        logger.info("GET /funciones/{}", id);

        Funcion funcion = funcionService.findById(id);

        if (funcion == null) {

            logger.warn("Funcion no encontrada id={}", id);

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(FuncionDTO.fromModel(funcion));
    }


    @GetMapping("/pelicula/{peliculaId}")
    public ResponseEntity<List<FuncionDTO>> findByPeliculaId(@PathVariable Long peliculaId) {

        logger.info("GET /funciones/pelicula/{}", peliculaId);

        List<Funcion> funciones = funcionService.findByPeliculaId(peliculaId);

        List<FuncionDTO> dtos = funciones.stream()
                .map(FuncionDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<FuncionDTO>> findBySalaId(@PathVariable Long salaId) {

        logger.info("GET /funciones/sala/{}", salaId);

        List<Funcion> funciones = funcionService.findBySalaId(salaId);

        List<FuncionDTO> dtos = funciones.stream()
                .map(FuncionDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {

        logger.info("GET /funciones/{}/exists", id);

        return ResponseEntity.ok(funcionService.existsById(id));
    }


    @PostMapping
    public ResponseEntity<FuncionDTO> save(@RequestBody FuncionDTO funcionDTO) {

        logger.info("POST /funciones - peliculaId={}, salaId={}", funcionDTO.getPeliculaId(), funcionDTO.getSalaId());

        Funcion nuevaFuncion = funcionService.save(funcionDTO.toModel());

        logger.info(
                "Funcion creada correctamente id={}",
                nuevaFuncion.getId());

        return ResponseEntity.ok(
                FuncionDTO.fromModel(nuevaFuncion));
    }


    @PutMapping("/{id}")
    public ResponseEntity<FuncionDTO> update(@PathVariable Long id, @RequestBody FuncionDTO funcionDTO) {

        logger.info("PUT /funciones/{}",id);

        Funcion funcionActualizada = funcionService.update(id, funcionDTO.toModel());

        if (funcionActualizada == null) {

            logger.warn("Funcion no encontrada id={}", id);

            return ResponseEntity.notFound().build();
        }

        logger.info("Funcion actualizada correctamente id={}", funcionActualizada.getId());

        return ResponseEntity.ok(FuncionDTO.fromModel(funcionActualizada));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        logger.info("DELETE /funciones/{}", id);

        Funcion funcion = funcionService.findById(id);

        if (funcion == null) {

            logger.warn("Funcion no encontrada id={}", id);

            return ResponseEntity.notFound().build();
        }

        funcionService.delete(id);

        logger.info("Funcion eliminada correctamente id={}", id);

        return ResponseEntity.noContent().build();
    }
}