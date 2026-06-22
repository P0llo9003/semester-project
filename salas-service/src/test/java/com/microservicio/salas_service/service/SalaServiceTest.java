package com.microservicio.salas_service.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.salas_service.model.Sala;
import com.microservicio.salas_service.repository.SalaRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    private SalasService salasService;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() throws Exception {

        salasService = new SalasService(salaRepository, webClient);

        Field field = SalasService.class.getDeclaredField("cinePath");
        field.setAccessible(true);
        field.set(salasService, "/cines/%d/exists");
    }

    private void mockCineExistente() {

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class))
                .thenReturn(Mono.just(Boolean.TRUE));
    }

    @Test
    void testSave() {

        mockCineExistente();

        Sala sala = new Sala(
                1L,
                "Sala Premium",
                100,
                "IMAX",
                1L);

        when(salaRepository.save(any(Sala.class)))
                .thenReturn(sala);

        Sala resultado = salasService.save(sala);

        assertNotNull(resultado);
        assertEquals("Sala Premium", resultado.getNombre());

        verify(salaRepository).save(any(Sala.class));
    }

    @Test
    void testFindAll() {

        when(salaRepository.findAll())
                .thenReturn(List.of(
                        new Sala(1L, "Sala 1", 80, "2D", 1L)));

        List<Sala> salas = salasService.findAll();

        assertNotNull(salas);
        assertEquals(1, salas.size());

        verify(salaRepository).findAll();
    }

    @Test
    void testFindById() {

        Long id = 1L;

        Sala sala = new Sala(
                id,
                "Sala IMAX",
                120,
                "IMAX",
                1L);

        when(salaRepository.findById(id))
                .thenReturn(Optional.of(sala));

        Sala resultado = salasService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(salaRepository).findById(id);
    }

    @Test
    void testFindByTipo() {

        when(salaRepository.findByTipo("IMAX"))
                .thenReturn(List.of(
                        new Sala(1L, "Sala IMAX", 120, "IMAX", 1L)));

        List<Sala> salas = salasService.findByTipo("IMAX");

        assertNotNull(salas);
        assertEquals(1, salas.size());

        verify(salaRepository).findByTipo("IMAX");
    }

    @Test
    void testUpdate() {

        Long id = 1L;

        Sala existente = new Sala(
                id,
                "Sala 1",
                100,
                "2D",
                1L);

        when(salaRepository.findById(id))
                .thenReturn(Optional.of(existente));

        Sala cambios = new Sala(
                id,
                "Sala Actualizada",
                150,
                "IMAX",
                2L);

        Sala guardada = new Sala(
                id,
                "Sala Actualizada",
                150,
                "IMAX",
                2L);

        when(salaRepository.save(any(Sala.class)))
                .thenReturn(guardada);

        Sala resultado = salasService.update(id, cambios);

        assertNotNull(resultado);
        assertEquals("Sala Actualizada", resultado.getNombre());
        assertEquals(150, resultado.getCapacidad());
        assertEquals("IMAX", resultado.getTipo());

        verify(salaRepository).findById(id);
        verify(salaRepository).save(any(Sala.class));
    }

    @Test
    void testDelete() {

        Long id = 1L;

        Sala sala = new Sala(
                id,
                "Sala 1",
                100,
                "2D",
                1L);

        when(salaRepository.findById(id))
                .thenReturn(Optional.of(sala));

        salasService.delete(id);

        verify(salaRepository).delete(sala);
    }

    @Test
    void testExistById() {

        Long id = 1L;

        when(salaRepository.existsById(id))
                .thenReturn(true);

        boolean existe = salasService.existById(id);

        assertTrue(existe);

        verify(salaRepository).existsById(id);
    }
}