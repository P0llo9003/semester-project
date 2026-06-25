package com.microservicio.funciones_service.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
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

import com.microservicio.funciones_service.model.Funcion;
import com.microservicio.funciones_service.repository.FuncionRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class FuncionServiceTest {

    private FuncionService funcionService;

    @Mock
    private FuncionRepository funcionRepository;

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

        funcionService = new FuncionService(funcionRepository, webClient);

        Field peliculaField =
                FuncionService.class.getDeclaredField("peliculaPath");

        peliculaField.setAccessible(true);
        peliculaField.set(funcionService, "http://api/peliculas/%d/exists");

        Field salaField =
                FuncionService.class.getDeclaredField("salaPath");

        salaField.setAccessible(true);
        salaField.set(funcionService, "http://api/salas/%d/exists");
    }

    private void mockValidacionesOk() {

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
                .thenReturn(requestHeadersSpec);

        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.bodyToMono(Boolean.class))
                .thenReturn(Mono.just(Boolean.TRUE));
    }

    @Test
    void testFindAll() {

        when(funcionRepository.findAll())
                .thenReturn(List.of(
                        new Funcion(
                                1L,
                                1L,
                                1L,
                                LocalDate.now(),
                                LocalTime.of(20, 0),
                                "ACTIVA")));

        List<Funcion> funciones = funcionService.findAll();

        assertNotNull(funciones);
        assertEquals(1, funciones.size());

        verify(funcionRepository).findAll();
    }

    @Test
    void testFindById() {

        Long id = 1L;

        Funcion funcion =
                new Funcion(
                        id,
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalTime.of(20, 0),
                        "ACTIVA");

        when(funcionRepository.findById(id))
                .thenReturn(Optional.of(funcion));

        Funcion resultado = funcionService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(funcionRepository).findById(id);
    }

    @Test
    void testSave() {

        mockValidacionesOk();

        Funcion funcion =
                new Funcion(
                        1L,
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalTime.of(20, 0),
                        "ACTIVA");

        when(funcionRepository.save(any(Funcion.class)))
                .thenReturn(funcion);

        Funcion resultado = funcionService.save(funcion);

        assertNotNull(resultado);

        verify(funcionRepository).save(any(Funcion.class));
    }

    @Test
    void testUpdate() {

        mockValidacionesOk();

        Long id = 1L;

        Funcion existente =
                new Funcion(
                        id,
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalTime.of(20, 0),
                        "ACTIVA");

        Funcion cambios =
                new Funcion(
                        id,
                        2L,
                        2L,
                        LocalDate.now().plusDays(1),
                        LocalTime.of(22, 0),
                        "MODIFICADA");

        when(funcionRepository.findById(id))
                .thenReturn(Optional.of(existente));

        when(funcionRepository.save(any(Funcion.class)))
                .thenReturn(cambios);

        Funcion resultado = funcionService.update(id, cambios);

        assertNotNull(resultado);
        assertEquals("MODIFICADA", resultado.getEstado());

        verify(funcionRepository).findById(id);
        verify(funcionRepository).save(any(Funcion.class));
    }

    @Test
    void testDelete() {

        Long id = 1L;

        funcionService.delete(id);

        verify(funcionRepository).deleteById(id);
    }

    @Test
    void testExistsById() {

        when(funcionRepository.existsById(1L))
                .thenReturn(true);

        Boolean existe = funcionService.existsById(1L);

        assertTrue(existe);

        verify(funcionRepository).existsById(1L);
    }
}