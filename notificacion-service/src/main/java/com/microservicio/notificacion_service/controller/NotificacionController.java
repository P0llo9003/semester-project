package com.microservicio.notificacion_service.controller;

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

import com.microservicio.notificacion_service.dto.NotificacionDTO;
import com.microservicio.notificacion_service.model.Notificacion;
import com.microservicio.notificacion_service.service.NotificacionService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificacionController.class);

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listarNotificaciones() {

        logger.info("GET /notificaciones");

        List<Notificacion> notificaciones = notificacionService.findAll();

        List<NotificacionDTO> dtos = notificaciones.stream()
                .map(NotificacionDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> findById(@PathVariable Long id) {

        logger.info("GET /notificaciones/{}", id);

        Notificacion notificacion = notificacionService.findById(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }

    @PostMapping
    public ResponseEntity<NotificacionDTO> crearNotificacion(
            @RequestBody NotificacionDTO notificacionDTO) {

        logger.info("POST /notificaciones");

        Notificacion nueva =
                notificacionService.save(notificacionDTO.toModel());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(NotificacionDTO.fromModel(nueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizar(
            @PathVariable Long id,
            @RequestBody Notificacion notificacion) {

        logger.info("PUT /notificaciones/{}", id);

        Notificacion actualizada =
                notificacionService.update(id, notificacion);

        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        logger.info("DELETE /notificaciones/{}", id);

        Notificacion notificacion =
                notificacionService.findById(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        notificacionService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeNotificacion(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificacionService.existById(id));
    }
}