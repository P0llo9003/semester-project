package com.microservicio.notificacion_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.notificacion_service.model.Notificacion;
import com.microservicio.notificacion_service.repository.NotificacionRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    private NotificacionService notificacionService;

    @Mock
    private NotificacionRepository notificacionRepository;

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
        notificacionService =
                new NotificacionService(
                        notificacionRepository,
                        webClient);

        Field field =
                NotificacionService.class.getDeclaredField("usuarioPath");
        field.setAccessible(true);
        field.set(notificacionService, "/usuarios/%d/exists");
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

        Notificacion notificacion =
                new Notificacion(
                        1L,
                        1L,
                        "Compra Exitosa",
                        "Tu compra fue realizada correctamente",
                        "ENVIADA",
                        LocalDateTime.now());

        when(notificacionRepository.save(any(Notificacion.class)))
                .thenReturn(notificacion);

        Notificacion resultado =
                notificacionService.save(notificacion);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getUsuarioId());
        assertEquals("Compra Exitosa", resultado.getTitulo());
        assertEquals("ENVIADA", resultado.getEstado());

        verify(notificacionRepository).save(any(Notificacion.class));
    }

    @Test
    void testListar() {

        when(notificacionRepository.findAll())
                .thenReturn(List.of(
                        new Notificacion(
                                1L,
                                1L,
                                "Compra Exitosa",
                                "Tu compra fue realizada correctamente",
                                "ENVIADA",
                                LocalDateTime.now())));

        List<Notificacion> notificaciones =
                notificacionService.findAll();

        assertNotNull(notificaciones);
        assertEquals(1, notificaciones.size());

        verify(notificacionRepository).findAll();
    }

    @Test
    void testBuscarPorId() {

        Long id = 1L;

        Notificacion notificacion =
                new Notificacion(
                        id,
                        1L,
                        "Compra Exitosa",
                        "Tu compra fue realizada correctamente",
                        "ENVIADA",
                        LocalDateTime.now());

        when(notificacionRepository.findById(id))
                .thenReturn(Optional.of(notificacion));

        Notificacion resultado =
                notificacionService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(notificacionRepository).findById(id);
    }
}