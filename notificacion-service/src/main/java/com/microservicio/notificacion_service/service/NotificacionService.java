package com.microservicio.notificacion_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.notificacion_service.model.Notificacion;
import com.microservicio.notificacion_service.repository.NotificacionRepository;

@Service
public class NotificacionService {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository notificacionRepository;

    private final WebClient webClient;

    @Value("${api.usuario.exists}")
    private String usuarioPath;

    public NotificacionService(
            NotificacionRepository notificacionRepository,
            WebClient webClient) {

        this.notificacionRepository = notificacionRepository;
        this.webClient = webClient;
    }

    public Notificacion save(Notificacion notificacion) {

        Boolean existeUsuario;

        try {
            existeUsuario = webClient.get()
                    .uri(String.format(usuarioPath, notificacion.getUsuarioId()))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al validar usuario");
        }

        if (Boolean.FALSE.equals(existeUsuario)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        logger.info("Guardando notificacion para usuario {}",
                notificacion.getUsuarioId());

        Notificacion notificacionGuardada =
                notificacionRepository.save(notificacion);

        logger.info("Notificacion guardada con id={}",
                notificacionGuardada.getId());

        return notificacionGuardada;
    }

    public List<Notificacion> findAll() {

        logger.info("Listando todas las notificaciones");

        return notificacionRepository.findAll();
    }

    public Notificacion findById(Long id) {

        logger.info("Buscando notificacion id={}", id);

        return notificacionRepository.findById(id).orElse(null);
    }

    public Notificacion update(Long id,
            Notificacion notificacionActualizada) {

        logger.info("Actualizando notificacion id={}", id);

        Boolean existeUsuario;

        try {
            existeUsuario = webClient.get()
                    .uri(String.format(
                            usuarioPath,
                            notificacionActualizada.getUsuarioId()))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al validar usuario");
        }

        if (Boolean.FALSE.equals(existeUsuario)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Notificacion notificacion =
                notificacionRepository.findById(id).orElse(null);

        if (notificacion != null) {

            notificacion.setUsuarioId(
                    notificacionActualizada.getUsuarioId());

            notificacion.setTitulo(
                    notificacionActualizada.getTitulo());

            notificacion.setMensaje(
                    notificacionActualizada.getMensaje());

            notificacion.setEstado(
                    notificacionActualizada.getEstado());

            notificacion.setFechaEnvio(
                    notificacionActualizada.getFechaEnvio());

            logger.info("Notificacion actualizada id={}", id);

            return notificacionRepository.save(notificacion);
        }

        logger.warn("No se encontro notificacion id={}", id);

        return null;
    }

    public void delete(Long id) {

        logger.info("Eliminando notificacion id={}", id);

        notificacionRepository.deleteById(id);

        logger.info("Notificacion eliminada id={}", id);
    }

    public boolean existById(Long id) {

        logger.info("Verificando existencia de notificacion id={}", id);

        return notificacionRepository.existsById(id);
    }
}