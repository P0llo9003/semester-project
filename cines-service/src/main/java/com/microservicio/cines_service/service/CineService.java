package com.microservicio.cines_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservicio.cines_service.exception.ResourceNotFoundException;
import com.microservicio.cines_service.model.Cine;
import com.microservicio.cines_service.repository.CineRepository;


@Service
public class CineService {

    private final CineRepository cineRepository;
    private static final Logger logger = LoggerFactory.getLogger(CineService.class);

    public CineService(CineRepository cineRepository) {
        this.cineRepository = cineRepository;
    }

    public Cine save(Cine cine) {
        logger.info("Iniciando guardar cine con idCine-{}, Nombre={}, Direccion={}, Ciudad={}, Telefono={}",
            cine.getId(), cine.getNombre(), cine.getDireccion(), cine.getCiudad(), cine.getTelefono()
        );
        return cineRepository.save(cine);
    }

    public List<Cine> findAll() {
        logger.info("Listando todos los cines");
        List<Cine> cines = cineRepository.findAll();
        logger.info("Total cines encontrados: {}", cines.size());
        return cines;
    }

    public Cine findById(Long id) {
        logger.info("Buscando cine con id={}", id);
        Cine cine = cineRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cine no encontrado id={}", id);
                    return new ResourceNotFoundException("Cine no existe");
                });
        logger.info("Cine encontrado id={}", id);
        return cine;
    }

    public Cine findByTelefono(int telefono) {
        logger.info("Buscando cine con telefono={}", telefono);
        Cine cine = cineRepository.findByTelefono(telefono)
                .orElseThrow(() -> {
                    logger.warn("Cine no encontrado telefono={}", telefono);
                    return new ResourceNotFoundException("Cine no existe");
                });
        return cine;
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
        logger.info("Iniciando eliminacion del cine id={}", id);
        try {
            if (!cineRepository.existsById(id)) {
                logger.warn("Cine no existe para eliminar id={}", id);
                throw new ResourceNotFoundException("Cine no existe");
            }
            cineRepository.deleteById(id);
            logger.info("Cine eliminado exitosamente id={}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar cine id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public boolean existById(Long id){
        return cineRepository.existsById(id);
    }

}