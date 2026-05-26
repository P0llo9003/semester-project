package com.microservicio.salas_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.salas_service.model.Sala;
import com.microservicio.salas_service.repository.SalaRepository;

@Service
public class SalasService {

    private final SalaRepository salaRepository;
    private final WebClient webClient;

    @Value("${api.cine.exists}")
    private String cinePath;

    public SalasService(SalaRepository salaRepository, WebClient webClient) {

        this.salaRepository = salaRepository;
        this.webClient = webClient;
    }


    public List<Sala> findAll() {
        return salaRepository.findAll();
    }


    public Sala findById(Long id) {

        return salaRepository.findById(id)
                .orElse(null);
    }


    public List<Sala> findByTipo(String tipo) {

        return salaRepository.findByTipo(tipo);
    }


    public List<Sala> findByCine(Long cineId) {

        Boolean existeCine;

        try {
            existeCine = webClient.get()
                    .uri(String.format(cinePath, cineId))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al validar cine");
        }
        if (Boolean.FALSE.equals(existeCine)) {
            throw new RuntimeException("Cine no encontrado");
        }
        return salaRepository.findByCineId(cineId);
    }


    public Sala save(Sala sala) {
    Boolean existeCine;
    try {
        existeCine = webClient.get()
                .uri(String.format(cinePath, sala.getCineId()))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    } catch (Exception e) {
        throw new RuntimeException(
                "Error al validar cine");
    }
    if (Boolean.FALSE.equals(existeCine)) {
        throw new RuntimeException(
                "Cine no encontrado");
    }
    return salaRepository.save(sala);
}


    public Sala update(Long id, Sala salaActualizada) {

        Sala sala1 = findById(id);

        if (sala1 != null) {
            sala1.setNombre(salaActualizada.getNombre());
            sala1.setCapacidad(salaActualizada.getCapacidad());
            sala1.setTipo(salaActualizada.getTipo());
            sala1.setCineId(salaActualizada.getCineId());
            return salaRepository.save(sala1);
        }
        return null;
    }


    public void delete(Long id) {
        Sala sala = findById(id);
        if (sala != null) {
            salaRepository.delete(sala);
        }
    }


    public boolean existById(Long id){
        return salaRepository.existsById(id);
    }


    
}