package com.microservicio.funciones_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.funciones_service.model.Funcion;
import com.microservicio.funciones_service.repository.FuncionRepository;

@Service
public class FuncionService {

    private static final Logger logger = LoggerFactory.getLogger(FuncionService.class);

    private final FuncionRepository funcionRepository;
    private final WebClient webClient;

    @Value("${api.pelicula.exists}")
    private String peliculaPath;

    @Value("${api.sala.exists}")
    private String salaPath;

    public FuncionService(
            FuncionRepository funcionRepository,
            WebClient webClient) {

        this.funcionRepository = funcionRepository;
        this.webClient = webClient;
    }


    public List<Funcion> findAll() {

        logger.info("Listando todas las funciones");

        List<Funcion> funciones = funcionRepository.findAll();

        logger.debug("Cantidad de funciones encontradas: {}", funciones.size());

        return funciones;
    }


    public Funcion findById(Long id) {

        logger.info("Buscando funcion por id={}", id);
        Funcion funcion = funcionRepository.findById(id).orElse(null);
        if (funcion == null) {
            logger.warn("Funcion no encontrada id={}", id);
        }
        return funcion;
    }


    public List<Funcion> findByPeliculaId(Long peliculaId) {

        logger.info("Buscando funciones por peliculaId={}", peliculaId);
        return funcionRepository.findByPeliculaId(peliculaId);
    }


    public List<Funcion> findBySalaId(Long salaId) {

        logger.info("Buscando funciones por salaId={}", salaId);
        return funcionRepository.findBySalaId(salaId);
    }


    public Funcion save(Funcion funcion) {

        logger.info("Guardando funcion: peliculaId={}, salaId={}",
                    funcion.getPeliculaId(),funcion.getSalaId());

        validatePeliculaAndSala(funcion.getPeliculaId(), funcion.getSalaId());

        Funcion savedFuncion = funcionRepository.save(funcion);

        logger.info("Funcion guardada correctamente id={}",savedFuncion.getId());
        return savedFuncion;
    }


    public Funcion update(Long id, Funcion updatedFuncion) {

        logger.info("Actualizando funcion id={}", id);
        Funcion existingFuncion = funcionRepository.findById(id).orElse(null);

        if (existingFuncion == null) {
            logger.warn("Funcion no encontrada id={}", id);
            return null;
        }

        validatePeliculaAndSala(updatedFuncion.getPeliculaId(),updatedFuncion.getSalaId());

        existingFuncion.setPeliculaId(updatedFuncion.getPeliculaId());
        existingFuncion.setSalaId(updatedFuncion.getSalaId());
        existingFuncion.setFecha(updatedFuncion.getFecha());
        existingFuncion.setHora(updatedFuncion.getHora());
        existingFuncion.setPrecio(updatedFuncion.getPrecio());
        existingFuncion.setEstado(updatedFuncion.getEstado());

        Funcion savedFuncion = funcionRepository.save(existingFuncion);

        logger.info(
                "Funcion actualizada correctamente id={}",
                savedFuncion.getId());

        return savedFuncion;
    }


    public void delete(Long id) {

        logger.info("Eliminando funcion id={}", id);
        funcionRepository.deleteById(id);
        logger.info("Funcion eliminada correctamente id={}", id);
    }


    // VALIDACIONES
    private void validatePeliculaAndSala(
            Long peliculaId,
            Long salaId) {

        Boolean peliculaExists;
        Boolean salaExists;

        // VALIDAR PELICULA
        try {

            logger.debug("Validando pelicula id={}", peliculaId);

            peliculaExists = webClient.get()
                    .uri(String.format(peliculaPath, peliculaId))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            logger.debug("Respuesta validacion pelicula={}", peliculaExists);

        } catch (Exception e) {

            logger.error("Error validando pelicula id={}", peliculaId, e);

            throw new RuntimeException("Error al validar pelicula");
        }


        // VALIDAR SALA
        try {

            logger.debug("Validando sala id={}", salaId);

            salaExists = webClient.get()
                    .uri(String.format(salaPath, salaId))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            logger.debug("Respuesta validacion sala={}", salaExists);

        } catch (Exception e) {

            logger.error("Error validando sala id={}", salaId, e);

            throw new RuntimeException("Error al validar sala");
        }


        // VALIDACIONES NULL
        if (peliculaExists == null) {

            logger.warn("Respuesta nula al validar pelicula id={}", peliculaId);

            throw new RuntimeException("No se pudo validar pelicula");
        }

        if (salaExists == null) {

            logger.warn("Respuesta nula al validar sala id={}", salaId);

            throw new RuntimeException("No se pudo validar sala");
        }


        // VALIDACIONES FALSE
        if (Boolean.FALSE.equals(peliculaExists)) {

            logger.warn("Pelicula no encontrada id={}", peliculaId);

            throw new RuntimeException("Pelicula no encontrada");
        }

        if (Boolean.FALSE.equals(salaExists)) {

            logger.warn("Sala no encontrada id={}", salaId);

            throw new RuntimeException("Sala no encontrada");
        }
    }


    public Boolean existsById(Long id) {

        logger.info("Verificando existencia de funcion id={}", id);
        return funcionRepository.existsById(id);
    }



    
}