package com.example.peliculasservice.controller;

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

import com.example.peliculasservice.dto.PeliculaDTO;
import com.example.peliculasservice.model.Pelicula;
import com.example.peliculasservice.service.PeliculaService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    private static final Logger logger = LoggerFactory.getLogger(PeliculaController.class);

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public ResponseEntity<List<PeliculaDTO>> listarPeliculas() {

        logger.info("GET /peliculas");

        List<Pelicula> peliculas = peliculaService.findAll();

        logger.debug("Cantidad de peliculas obtenidas: {}", peliculas.size());

        List<PeliculaDTO> dtos = peliculas.stream()
                        .map(PeliculaDTO::fromModel)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> findById(@PathVariable Long id) {

        logger.info("GET /peliculas/{}", id);
        Pelicula pelicula = peliculaService.findById(id);

        if (pelicula == null){
            logger.warn("Pelicula no encontrada id={}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pelicula);
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> crearCine(@RequestBody PeliculaDTO PeliculaDto) {

        logger.info("POST /peliculas - titulo={}", PeliculaDto.getTitulo());

        Pelicula nuevo = peliculaService.save(PeliculaDto.toModel());

        logger.info("Pelicula creada exitosamente id={}", nuevo.getId());

        return ResponseEntity.ok(PeliculaDTO.fromModel(nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizar(@PathVariable Long id, @RequestBody Pelicula Pelicula) {

        logger.info("PUT /peliculas/{}", id);

        Pelicula pelicula = peliculaService.update(id, Pelicula);

        if (pelicula == null) {

            logger.warn("No se pudo actualizar pelicula id={}", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("Pelicula actualizada exitosamente id={}", id);
        return ResponseEntity.ok(pelicula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {

        logger.info("DELETE /peliculas/{}", id);

        Pelicula pelicula = peliculaService.findById(id);

        if (pelicula == null) {

            logger.warn("Pelicula no encontrada id={}", id);
            return ResponseEntity.notFound().build();
        }
        peliculaService.delete(id);

        logger.info("Pelicula eliminada exitosamente id={}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existePelicula(@PathVariable Long id) {

        logger.info("GET /peliculas/{}/exists", id);
        return ResponseEntity.ok(peliculaService.existById(id));
    }
}