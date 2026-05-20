package com.microservicio.cines_service.controller;

import java.util.List;
import java.util.stream.Collectors;

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

    public CineController(CineService cineService) {
        this.cineService = cineService;
    }


    @GetMapping
    public ResponseEntity<List<CineDTO>> listarCines() {
        List<Cine> cines = cineService.findAll();
        List<CineDTO> dtos = cines.stream().map(CineDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cine> findById(@PathVariable Long id) {
        Cine cine = cineService.findById(id);

        if (cine == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cine);
    }


	@PostMapping
	public ResponseEntity<CineDTO> crearCine(@RequestBody CineDTO CineDto) {
		Cine nuevo = cineService.save(CineDto.toModel());
		return ResponseEntity.ok(CineDTO.fromModel(nuevo));
	}


    @PutMapping("/{id}")
    public ResponseEntity<Cine> actualizar(@PathVariable Long id, @RequestBody Cine Cine) {

        Cine cine = cineService.update(id, Cine);
        if (cine == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cine);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {

        Cine cine = cineService.findById(id);
        if (cine == null) {
            return ResponseEntity.notFound().build();
        }
        cineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}