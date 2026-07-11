package com.microservicio.fidelizacion_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.fidelizacion_service.model.Fidelizacion;
import com.microservicio.fidelizacion_service.repository.FidelizacionRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class FidelizacionServiceTest {

    private fidelizacionService fidelizacionService;

    @Mock
    private FidelizacionRepository fidelizacionRepository;
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
        fidelizacionService =
                new fidelizacionService(
                        fidelizacionRepository,
                        webClient);

        Field field =
                fidelizacionService.class.getDeclaredField("usuarioPath");
        field.setAccessible(true);
        field.set(fidelizacionService, "/usuarios/%d/exists");
    }

    private void mockUsuarioExistente() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class))
                .thenReturn(Mono.just(Boolean.TRUE));
    }

    @Test
    void testGuardar() {

        mockUsuarioExistente();

        Fidelizacion fidelizacion =
                new Fidelizacion(1L, 1L, 100, "Bronce");

        when(fidelizacionRepository.save(any(Fidelizacion.class)))
                .thenReturn(fidelizacion);

        Fidelizacion resultado =
                fidelizacionService.save(fidelizacion);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getUsuarioId());
        assertEquals(100, resultado.getPuntos());
        assertEquals("Bronce", resultado.getNivel());

        verify(fidelizacionRepository).save(any(Fidelizacion.class));
    }

    @Test
    void testListar() {

        when(fidelizacionRepository.findAll())
                .thenReturn(List.of(
                        new Fidelizacion(1L, 1L, 100, "Bronce")));

        List<Fidelizacion> fidelizaciones =
                fidelizacionService.findAll();

        assertNotNull(fidelizaciones);
        assertEquals(1, fidelizaciones.size());

        verify(fidelizacionRepository).findAll();
    }

    @Test
    void testBuscarPorId() {

        Long id = 1L;

        Fidelizacion fidelizacion =
                new Fidelizacion(id, 1L, 100, "Bronce");

        when(fidelizacionRepository.findById(id))
                .thenReturn(Optional.of(fidelizacion));

        Fidelizacion resultado =
                fidelizacionService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(fidelizacionRepository).findById(id);
    }
}