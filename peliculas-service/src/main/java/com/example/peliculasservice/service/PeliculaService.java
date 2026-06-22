package com.example.peliculasservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.peliculasservice.model.Pelicula;
import com.example.peliculasservice.repository.PeliculaRepository;

@Service
public class PeliculaService {

    private static final Logger logger = LoggerFactory.getLogger(PeliculaService.class);

    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public Pelicula save(Pelicula pelicula) {

        logger.info("Guardando pelicula titulo={}",
                pelicula.getTitulo());

        Pelicula peliculaGuardada = peliculaRepository.save(pelicula);

        logger.info("Pelicula guardada exitosamente id={}",
                peliculaGuardada.getId());

        return peliculaGuardada;
    }

    public List<Pelicula> findAll() {

        logger.info("Listando todas las peliculas");

        List<Pelicula> peliculas = peliculaRepository.findAll();

        logger.debug("Cantidad de peliculas encontradas: {}",
                peliculas.size());

        return peliculas;
    }

    public Pelicula findById(Long id) {

        logger.info("Buscando pelicula id={}", id);

        Pelicula pelicula = peliculaRepository.findById(id).orElse(null);

        if (pelicula == null) {
            logger.warn("Pelicula no encontrada id={}", id);
        }

        return pelicula;
    }

    public Pelicula update(Long id, Pelicula updatedMovie) {

        logger.info("Actualizando pelicula id={}", id);

        Pelicula peli1 = peliculaRepository.findById(id).orElse(null);

        if (peli1 != null) {
            peli1.setTitulo(updatedMovie.getTitulo());
            peli1.setDescripcion(updatedMovie.getDescripcion());
            peli1.setDuracion(updatedMovie.getDuracion());
            peli1.setGenero(updatedMovie.getGenero());
            peli1.setClasificacion(updatedMovie.getClasificacion());

            Pelicula peliculaActualizada = peliculaRepository.save(peli1);

            logger.info(
                    "Pelicula actualizada exitosamente id={}",
                    id);

            return peliculaActualizada;
        }

        logger.warn(
                "No se pudo actualizar pelicula id={}", id);
        return null;
    }

    public void delete(Long id) {

        logger.info("Eliminando pelicula id={}", id);

        peliculaRepository.deleteById(id);

        logger.info("Pelicula eliminada exitosamente id={}", id);
    }

    public boolean existById(Long id){

        logger.info("Verificando existencia de pelicula id={}", id);
        return peliculaRepository.existsById(id);
    }

}