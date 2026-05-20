package com.microservicio.cines_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservicio.cines_service.model.Cine;
import com.microservicio.cines_service.repository.CineRepository;

@Service
public class CineService {

    private final CineRepository cineRepository;

    public CineService(CineRepository cineRepository) {
        this.cineRepository = cineRepository;
    }

    public Cine save(Cine cine) {
        return cineRepository.save(cine);
    }

    public List<Cine> findAll() {
        return cineRepository.findAll();
    }

    public Cine findById(Long id) {
        return cineRepository.findById(id).orElse(null);
    }

    public Cine update(Long id, Cine updatedCine) {

        Cine cine1 = cineRepository.findById(id).orElse(null);

        if (cine1 != null) {

            cine1.setNombre(updatedCine.getNombre());
            cine1.setDireccion(updatedCine.getDireccion());
            cine1.setCiudad(updatedCine.getCiudad());
            cine1.setTelefono(updatedCine.getTelefono());

            return cineRepository.save(cine1);
        }

        return null;
    }

    public void delete(Long id) {
        cineRepository.deleteById(id);
    }

}