package com.microservicio.funciones_service.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.microservicio.funciones_service.model.Entrada;
import com.microservicio.funciones_service.repository.EntradaRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class EntradaServiceTest {

    private EntradaService entradaService;

    @Mock
    private EntradaRepository entradaRepository;

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

        entradaService = new EntradaService(
                entradaRepository,
                webClient);

        Field usuarioField =
                EntradaService.class.getDeclaredField("usuarioPath");
        usuarioField.setAccessible(true);
        usuarioField.set(entradaService, "/usuarios/%d/exists");

        Field funcionField =
                EntradaService.class.getDeclaredField("funcionPath");
        funcionField.setAccessible(true);
        funcionField.set(entradaService, "/funciones/%d/exists");
    }

    private void mockValidaciones() {

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

        when(entradaRepository.findAll()).thenReturn(
                List.of(new Entrada(1L, 1L, 1L, "A1")));

        List<Entrada> entradas = entradaService.findAll();

        assertNotNull(entradas);
        assertEquals(1, entradas.size());

        verify(entradaRepository).findAll();
    }

    @Test
    void testFindById() {

        Long id = 1L;

        Entrada entrada =
                new Entrada(id, 1L, 1L, "A1");

        when(entradaRepository.findById(id))
                .thenReturn(Optional.of(entrada));

        Entrada resultado =
                entradaService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(entradaRepository).findById(id);
    }

    @Test
    void testSave() {

        mockValidaciones();

        Entrada entrada =
                new Entrada(1L, 1L, 1L, "A1");

        when(entradaRepository.save(any(Entrada.class)))
                .thenReturn(entrada);

        Entrada resultado =
                entradaService.save(entrada);

        assertNotNull(resultado);
        assertEquals("A1", resultado.getAsiento());

        verify(entradaRepository).save(any(Entrada.class));
    }

    @Test
    void testUpdate() {

        mockValidaciones();

        Long id = 1L;

        Entrada existente =
                new Entrada(id, 1L, 1L, "A1");

        when(entradaRepository.findById(id))
                .thenReturn(Optional.of(existente));

        Entrada cambios =
                new Entrada(id, 2L, 2L, "B5");

        Entrada guardada =
                new Entrada(id, 2L, 2L, "B5");

        when(entradaRepository.save(any(Entrada.class)))
                .thenReturn(guardada);

        Entrada resultado =
                entradaService.update(id, cambios);

        assertNotNull(resultado);
        assertEquals("B5", resultado.getAsiento());
        assertEquals(2L, resultado.getFuncionId());
        assertEquals(2L, resultado.getUsuarioId());

        verify(entradaRepository).findById(id);
        verify(entradaRepository).save(any(Entrada.class));
    }

    @Test
    void testDelete() {

        Long id = 1L;

        entradaService.delete(id);

        verify(entradaRepository).deleteById(id);
    }
}