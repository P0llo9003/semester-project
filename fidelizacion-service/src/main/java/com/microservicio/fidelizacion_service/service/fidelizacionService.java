package com.microservicio.fidelizacion_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import com.microservicio.fidelizacion_service.model.Fidelizacion;
import com.microservicio.fidelizacion_service.repository.FidelizacionRepository;

@Service
public class fidelizacionService {

    private static final Logger logger =
            LoggerFactory.getLogger(fidelizacionService.class);

    private final FidelizacionRepository fidelizacionRepository;

    private final WebClient webClient;

        @Value("${api.usuario.exists}")
        private String usuarioPath;

    public fidelizacionService(
        FidelizacionRepository fidelizacionRepository,
        WebClient webClient) {

    this.fidelizacionRepository = fidelizacionRepository;
    this.webClient = webClient;
}

    public Fidelizacion save(Fidelizacion fidelizacion) {

         Boolean existeUsuario;

    try {
        existeUsuario = webClient.get()
                .uri(String.format(usuarioPath, fidelizacion.getUsuarioId()))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    } catch (Exception e) {
        throw new RuntimeException("Error al validar usuario");
    }

    if (Boolean.FALSE.equals(existeUsuario)) {
        throw new RuntimeException("Usuario no encontrado");
    }

        logger.info("Guardando fidelizacion para usuario {}",
                fidelizacion.getUsuarioId());

        Fidelizacion fidelizacionGuardada =
                fidelizacionRepository.save(fidelizacion);

        logger.info("Fidelizacion guardada con id={}",
                fidelizacionGuardada.getId());

        return fidelizacionGuardada;
    }

    public List<Fidelizacion> findAll() {

        logger.info("Listando todas las fidelizaciones");

        return fidelizacionRepository.findAll();
    }

    public Fidelizacion findById(Long id) {

        logger.info("Buscando fidelizacion id={}", id);

        return fidelizacionRepository.findById(id).orElse(null);
    }

    public Fidelizacion update(Long id,
        Fidelizacion fidelizacionActualizada) {

    logger.info("Actualizando fidelizacion id={}", id);

    Boolean existeUsuario;

    try {
        existeUsuario = webClient.get()
                .uri(String.format(
                        usuarioPath,
                        fidelizacionActualizada.getUsuarioId()))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    } catch (Exception e) {
        throw new RuntimeException("Error al validar usuario");
    }

    if (Boolean.FALSE.equals(existeUsuario)) {
        throw new RuntimeException("Usuario no encontrado");
    }

    Fidelizacion fidelizacion =
            fidelizacionRepository.findById(id).orElse(null);

    if (fidelizacion != null) {

        fidelizacion.setUsuarioId(
                fidelizacionActualizada.getUsuarioId());

        fidelizacion.setPuntos(
                fidelizacionActualizada.getPuntos());

        fidelizacion.setNivel(
                fidelizacionActualizada.getNivel());

        logger.info("Fidelizacion actualizada id={}", id);

        return fidelizacionRepository.save(fidelizacion);
    }

    logger.warn("No se encontro fidelizacion id={}", id);

    return null;
}

    public void delete(Long id) {

        logger.info("Eliminando fidelizacion id={}", id);

        fidelizacionRepository.deleteById(id);

        logger.info("Fidelizacion eliminada id={}", id);
    }

    public boolean existById(Long id) {

        logger.info("Verificando existencia de fidelizacion id={}", id);

        return fidelizacionRepository.existsById(id);
    }
}