package com.microservicio.funciones_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.funciones_service.model.Entrada;
import com.microservicio.funciones_service.repository.EntradaRepository;

@Service
public class EntradaService {

    private static final Logger logger = LoggerFactory.getLogger(EntradaService.class);

    private final EntradaRepository entradaRepository;
    private final WebClient webClient;

    @Value("${api.usuario.exists}")
    private String usuarioPath;

    @Value("${api.funcion.exists}")
    private String funcionPath;

    public EntradaService(EntradaRepository entradaRepository,WebClient webClient) {

        this.entradaRepository = entradaRepository;
        this.webClient = webClient;
    }


    public List<Entrada> findAll() {

        logger.info("Listando todas las entradas");

        List<Entrada> entradas = entradaRepository.findAll();

        logger.debug(
                "Cantidad de entradas encontradas: {}",
                entradas.size());

        return entradas;
    }


    public Entrada findById(Long id) {

        logger.info("Buscando entrada por id={}", id);

        Entrada entrada = entradaRepository.findById(id).orElse(null);

        if (entrada == null) {
            logger.warn(
                    "Entrada no encontrada id={}",
                    id);
        }
        return entrada;
    }


    public Entrada save(Entrada entrada) {

        logger.info("Guardando entrada: funcionId={}, usuarioId={}", entrada.getFuncionId(), entrada.getUsuarioId());

        validateUsuarioAndFuncion(entrada.getUsuarioId(), entrada.getFuncionId());

        Entrada savedEntrada = entradaRepository.save(entrada);

        logger.info("Entrada guardada correctamente id={}", savedEntrada.getId());

        return savedEntrada;
    }


    public Entrada update(Long id, Entrada updatedEntrada) {

        logger.info("Actualizando entrada id={}", id);

        Entrada existingEntrada = entradaRepository.findById(id).orElse(null);

        if (existingEntrada == null) {
            logger.warn(
                    "Entrada no encontrada id={}",
                    id);
            return null;
        }

        validateUsuarioAndFuncion(updatedEntrada.getUsuarioId(), updatedEntrada.getFuncionId());

        existingEntrada.setFuncionId(updatedEntrada.getFuncionId());
        existingEntrada.setUsuarioId(updatedEntrada.getUsuarioId());
        existingEntrada.setAsiento(updatedEntrada.getAsiento());

        Entrada savedEntrada = entradaRepository.save(existingEntrada);

        logger.info(
                "Entrada actualizada correctamente id={}",
                savedEntrada.getId());

        return savedEntrada;
    }


    public void delete(Long id) {

        logger.info("Eliminando entrada id={}", id);

        entradaRepository.deleteById(id);

        logger.info(
                "Entrada eliminada correctamente id={}",
                id);
    }


    // VALIDACIONES
    private void validateUsuarioAndFuncion(
            Long usuarioId,
            Long funcionId) {

        Boolean usuarioExists;
        Boolean funcionExists;


        // VALIDAR USUARIO
        try {

            logger.debug("Validando usuario id={}", usuarioId);

            usuarioExists = webClient.get()
                    .uri(String.format(usuarioPath, usuarioId))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            logger.debug("Respuesta validacion usuario={}", usuarioExists);

        } catch (Exception e) {

            logger.error("Error validando usuario id={}", usuarioId, e);

            throw new RuntimeException("Error al validar usuario");
        }


        // VALIDAR FUNCION
        try {

            logger.debug("Validando funcion id={}", funcionId);

            funcionExists = webClient.get()
                    .uri(String.format(funcionPath, funcionId))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            logger.debug("Respuesta validacion funcion={}", funcionExists);

        } catch (Exception e) {

            logger.error("Error validando funcion id={}", funcionId, e);

            throw new RuntimeException("Error al validar funcion");
        }


        // VALIDACIONES NULL
        if (usuarioExists == null) {

            logger.warn("Respuesta nula al validar usuario id={}", usuarioId);

            throw new RuntimeException("No se pudo validar usuario");
        }

        if (funcionExists == null) {

            logger.warn("Respuesta nula al validar funcion id={}", funcionId);

            throw new RuntimeException("No se pudo validar funcion");
        }


        // VALIDACIONES FALSE
        if (Boolean.FALSE.equals(usuarioExists)) {

            logger.warn("Usuario no encontrado id={}", usuarioId);

            throw new RuntimeException("Usuario no encontrado");
        }

        if (Boolean.FALSE.equals(funcionExists)) {

            logger.warn("Funcion no encontrada id={}", funcionId);

            throw new RuntimeException("Funcion no encontrada");
        }
    }




    public Boolean existsById(Long id) {

        logger.info("Verificando existencia de entrada id={}", id);
        return entradaRepository.existsById(id);
    }    

}