package com.microservicio.empleados_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservicio.empleados_service.dto.EmpleadoDTO;
import com.microservicio.empleados_service.model.Empleado;
import com.microservicio.empleados_service.service.EmpleadoService;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private static final Logger logger =
            LoggerFactory.getLogger(EmpleadoController.class);

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleados() {

        logger.info("GET /empleados");

        List<Empleado> empleados = empleadoService.findAll();

        List<EmpleadoDTO> dtos = empleados.stream()
                .map(EmpleadoDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> findById(@PathVariable Long id) {

        logger.info("GET /empleados/{}", id);

        Empleado empleado = empleadoService.findById(id);

        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empleado);
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<Empleado> findByRun(@PathVariable int run) {

        logger.info("GET /empleados/run/{}", run);

        Empleado empleado = empleadoService.findByRun(run);

        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(
            @RequestBody EmpleadoDTO empleadoDTO) {

        logger.info("POST /empleados");

        Empleado nuevo =
                empleadoService.save(empleadoDTO.toModel());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EmpleadoDTO.fromModel(nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(
            @PathVariable Long id,
            @RequestBody Empleado empleado) {

        logger.info("PUT /empleados/{}", id);

        Empleado actualizado =
                empleadoService.update(id, empleado);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        logger.info("DELETE /empleados/{}", id);

        Empleado empleado =
                empleadoService.findById(id);

        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }

        empleadoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeEmpleado(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                empleadoService.existById(id));
    }
}