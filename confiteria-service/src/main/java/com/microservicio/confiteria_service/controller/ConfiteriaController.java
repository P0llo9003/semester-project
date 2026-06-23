package com.microservicio.confiteria_service.controller;


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

import com.microservicio.confiteria_service.dto.ConfiteriaDTO;
import com.microservicio.confiteria_service.model.Confiteria;
import com.microservicio.confiteria_service.service.ConfiteriaService;



@RestController
@RequestMapping("/confiteria")
public class ConfiteriaController {

    private final ConfiteriaService confiteriaService;

    private static final Logger logger = LoggerFactory.getLogger(ConfiteriaController.class);

    public ConfiteriaController(ConfiteriaService confiteriaService) {
        this.confiteriaService = confiteriaService;
    }
   
    @GetMapping
    public ResponseEntity<List<ConfiteriaDTO>> listarProductos() {
        
        logger.info("GET /confiteria - Listando Productos");

        List<Confiteria> confi = confiteriaService.findAll();

        List<ConfiteriaDTO> dtos = confi.stream()
                .map(ConfiteriaDTO::fromModel)
                .collect(Collectors.toList());

        logger.info("Total de productos encontrados: {}", dtos.size());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Confiteria> findById(@PathVariable Long id) {

        logger.info("GET /confiteria/{} - Buscando producto", id);

        Confiteria confi = confiteriaService.findById(id);

        if (confi == null) {
            logger.warn("Producto no encontrado id={}", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("Producto encontrado id={}", id);
        return ResponseEntity.ok(confi);
    }


    @PostMapping
    public ResponseEntity<ConfiteriaDTO> crearCine(@RequestBody ConfiteriaDTO confiteriaDto) {

        try {

            logger.info("POST /confiteria - Creando producto");
            Confiteria nuevo = confiteriaService.save(confiteriaDto.toModel());
            logger.info("Producto creado exitosamente id={}",
                    nuevo.getId());

            return ResponseEntity.ok(
                    ConfiteriaDTO.fromModel(nuevo));

        } catch (Exception e) {
            logger.error("Error al crear producto: {}",
                    e.getMessage(), e);
            throw e;
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Confiteria> actualizar(@PathVariable Long id, @RequestBody Confiteria confiteria) {

        logger.info("PUT /confiteria/{} - Actualizando producto", id);
        try {
            Confiteria confi = confiteriaService.update(id, confiteria);
            if (confi == null) {
                logger.warn("Producto no encontrado id={}", id);
                return ResponseEntity.notFound().build();
            }

            logger.info("Prodcuto actualizado exitosamente id={}", id);
            return ResponseEntity.ok(confi);
        } catch (Exception e) {
            logger.error("Error al actualizar producto id={}: {}",
                    id, e.getMessage(), e);
            throw e;
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {

        logger.info("DELETE /confiteria/{} - Eliminando producto", id);

        try {
            Confiteria confi = confiteriaService.findById(id);
            if (confi == null) {
                logger.warn("Producto no encontrado id={}", id);
                return ResponseEntity.notFound().build();
            }

            confiteriaService.delete(id);
            logger.info("Producto eliminado exitosamente id={}", id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            logger.error("Error al eliminar Producto id={}: {}",
                    id, e.getMessage(), e);
            throw e;
        }
    }

}
