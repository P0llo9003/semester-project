package com.microservicio.salas_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.salas_service.model.Sala;
import com.microservicio.salas_service.repository.SalaRepository;

@Service
public class SalasService {

    private static final Logger logger = LoggerFactory.getLogger(SalasService.class);

    private final SalaRepository salaRepository;
    private final WebClient webClient;

    @Value("${api.cine.exists}")
    private String cinePath;

    public SalasService(SalaRepository salaRepository, WebClient webClient) {

        this.salaRepository = salaRepository;
        this.webClient = webClient;
    }

    public List<Sala> findAll() {

        logger.info("Listando todas las salas");

        List<Sala> salas = salaRepository.findAll();

        logger.debug("Cantidad de salas encontradas: {}", salas.size());
        return salas;
    }

    public Sala findById(Long id) {

        logger.info("Buscando sala id={}", id);

        Sala sala = salaRepository.findById(id)
                .orElse(null);

        if (sala == null) {
            logger.warn("Sala no encontrada id={}", id);
        }
        return sala;
    }

    public List<Sala> findByTipo(String tipo) {

        logger.info("Buscando salas por tipo={}", tipo);

        List<Sala> salas = salaRepository.findByTipo(tipo);

        logger.debug("Cantidad de salas encontradas: {}", salas.size());
        return salas;
    }

    public List<Sala> findByCine(Long cineId) {

        logger.info("Buscando salas por cineId={}", cineId);

        Boolean existeCine;

        try {
            logger.info("Validando existencia de cine id={}", cineId);

            existeCine = webClient.get()
                    .uri(String.format(cinePath, cineId))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            logger.info("Respuesta validacion cine={}", existeCine);

        } catch (Exception e) {
            logger.error("Error al validar cine id={}", cineId, e);

            throw new RuntimeException(
                    "Error al validar cine");
        }
        if (Boolean.FALSE.equals(existeCine)) {
            logger.warn("Cine no encontrado id={}", cineId);

            throw new RuntimeException("Cine no encontrado");
        }
        List<Sala> salas = salaRepository.findByCineId(cineId);

        logger.debug("Cantidad de salas encontradas: {}", salas.size());
        return salas;
    }

    public Sala save(Sala sala) {

        logger.info("Guardando sala nombre={}, cineId={}", sala.getNombre(), sala.getCineId());

        Boolean existeCine;

        try {
            logger.info("Validando existencia de cine id={}", sala.getCineId());

            existeCine = webClient.get()
                    .uri(String.format(
                            cinePath,
                            sala.getCineId()))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            logger.info("Respuesta validacion cine={}", existeCine);

        } catch (Exception e) {
            logger.error("Error al validar cine id={}", sala.getCineId(), e);

            throw new RuntimeException("Error al validar cine");
        }

        if (Boolean.FALSE.equals(existeCine)) {

            logger.warn("Cine no encontrado id={}", sala.getCineId());

            throw new RuntimeException("Cine no encontrado");
        }
        Sala salaGuardada = salaRepository.save(sala);

        logger.info("Sala guardada exitosamente id={}", salaGuardada.getId());
        return salaGuardada;
    }

    public Sala update(Long id, Sala salaActualizada) {

        logger.info("Actualizando sala id={}", id);

        Sala sala1 = findById(id);

        if (sala1 != null) {
            sala1.setNombre(salaActualizada.getNombre());
            sala1.setCapacidad(salaActualizada.getCapacidad());
            sala1.setTipo(salaActualizada.getTipo());
            sala1.setCineId(salaActualizada.getCineId());

            Sala salaGuardada = salaRepository.save(sala1);

            logger.info("Sala actualizada exitosamente id={}", id);
            return salaGuardada;
        }

        logger.warn("No se pudo actualizar sala id={}", id);
        return null;
    }

    public void delete(Long id) {

        logger.info("Eliminando sala id={}", id);

        Sala sala = findById(id);

        if (sala != null) {
            salaRepository.delete(sala);

            logger.info("Sala eliminada exitosamente id={}", id);
        } else {

            logger.warn("No se pudo eliminar sala id={}", id);
        }
    }

    public boolean existById(Long id){

        logger.info("Verificando existencia de sala id={}", id);

        return salaRepository.existsById(id);
    }
}