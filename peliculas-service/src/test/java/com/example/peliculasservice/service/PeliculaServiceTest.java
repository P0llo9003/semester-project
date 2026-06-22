package com.example.peliculasservice.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.peliculasservice.model.Pelicula;
import com.example.peliculasservice.repository.PeliculaRepository;

@ExtendWith(MockitoExtension.class)
class PeliculaServiceTest {

    private PeliculaService peliculaService;

    @Mock
    private PeliculaRepository peliculaRepository;

    @BeforeEach
    void setUp() {
        peliculaService = new PeliculaService(peliculaRepository);
    }

    @Test
    void testSave() {

        Pelicula pelicula = new Pelicula(
                1L,
                "Avatar",
                "Pelicula de ciencia ficcion",
                180,
                "Accion",
                "+14"
        );

        when(peliculaRepository.save(any(Pelicula.class))).thenReturn(pelicula);

        Pelicula resultado = peliculaService.save(pelicula);

        assertNotNull(resultado);
        assertEquals("Avatar", resultado.getTitulo());

        verify(peliculaRepository).save(any(Pelicula.class));
    }

    @Test
    void testFindAll() {

        when(peliculaRepository.findAll()).thenReturn(
                List.of(
                        new Pelicula(
                                1L,
                                "Avatar",
                                "Pelicula de ciencia ficcion",
                                180,
                                "Accion",
                                "+14"
                        )
                )
        );

        List<Pelicula> peliculas = peliculaService.findAll();

        assertNotNull(peliculas);
        assertEquals(1, peliculas.size());

        verify(peliculaRepository).findAll();
    }

    @Test
    void testFindById() {

        Long id = 1L;

        Pelicula pelicula = new Pelicula(
                id,
                "Avatar",
                "Pelicula de ciencia ficcion",
                180,
                "Accion",
                "+14"
        );

        when(peliculaRepository.findById(id))
                .thenReturn(Optional.of(pelicula));

        Pelicula resultado = peliculaService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(peliculaRepository).findById(id);
    }

    @Test
    void testUpdate() {

        Long id = 1L;

        Pelicula existente = new Pelicula(
                id,
                "Avatar",
                "Descripcion",
                180,
                "Accion",
                "+14"
        );

        Pelicula cambios = new Pelicula(
                id,
                "Titanic",
                "Drama romantico",
                190,
                "Drama",
                "+18"
        );

        when(peliculaRepository.findById(id))
                .thenReturn(Optional.of(existente));

        when(peliculaRepository.save(any(Pelicula.class)))
                .thenReturn(cambios);

        Pelicula resultado = peliculaService.update(id, cambios);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Titanic", resultado.getTitulo());
        assertEquals("Drama", resultado.getGenero());

        verify(peliculaRepository).findById(id);
        verify(peliculaRepository).save(any(Pelicula.class));
    }

    @Test
    void testDelete() {

        Long id = 1L;

        doNothing().when(peliculaRepository).deleteById(id);

        peliculaService.delete(id);

        verify(peliculaRepository).deleteById(id);
    }

    @Test
    void testExistById() {

        Long id = 1L;

        when(peliculaRepository.existsById(id))
                .thenReturn(true);

        boolean existe = peliculaService.existById(id);

        assertTrue(existe);

        verify(peliculaRepository).existsById(id);
    }
}