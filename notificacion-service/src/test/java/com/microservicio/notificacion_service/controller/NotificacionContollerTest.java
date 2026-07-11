package com.microservicio.notificacion_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microservicio.notificacion_service.dto.NotificacionDTO;
import com.microservicio.notificacion_service.model.Notificacion;
import com.microservicio.notificacion_service.service.NotificacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class NotificacionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificacionService notificacionService;

    private ObjectMapper objectMapper;

    private Notificacion notificacion;
    private NotificacionDTO notificacionDTO;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        notificacion =
                new Notificacion(
                        1L,
                        1L,
                        "Compra Exitosa",
                        "Tu compra fue realizada correctamente",
                        "ENVIADA",
                        LocalDateTime.now());

        notificacionDTO =
                new NotificacionDTO(
                        1L,
                        1L,
                        "Compra Exitosa",
                        "Tu compra fue realizada correctamente",
                        "ENVIADA",
                        LocalDateTime.now());

        NotificacionController controller =
                new NotificacionController(notificacionService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void testListarNotificaciones() throws Exception {

        when(notificacionService.findAll())
                .thenReturn(List.of(notificacion));

        mockMvc.perform(get("/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].usuarioId").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Compra Exitosa"))
                .andExpect(jsonPath("$[0].estado").value("ENVIADA"));
    }

    @Test
    void testBuscarPorId() throws Exception {

        when(notificacionService.findById(1L))
                .thenReturn(notificacion);

        mockMvc.perform(get("/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.titulo").value("Compra Exitosa"))
                .andExpect(jsonPath("$.estado").value("ENVIADA"));
    }

    @Test
    void testCrearNotificacion() throws Exception {

        when(notificacionService.save(any(Notificacion.class)))
                .thenReturn(notificacion);

        mockMvc.perform(post("/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificacionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.titulo").value("Compra Exitosa"))
                .andExpect(jsonPath("$.estado").value("ENVIADA"));
    }

    @Test
    void testActualizarNotificacion() throws Exception {

        when(notificacionService.update(eq(1L), any(Notificacion.class)))
                .thenReturn(notificacion);

        mockMvc.perform(put("/notificaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.titulo").value("Compra Exitosa"))
                .andExpect(jsonPath("$.estado").value("ENVIADA"));
    }

    @Test
    void testEliminarNotificacion() throws Exception {

        when(notificacionService.findById(1L))
                .thenReturn(notificacion);

        doNothing().when(notificacionService).delete(1L);

        mockMvc.perform(delete("/notificaciones/1"))
                .andExpect(status().isNoContent());

        verify(notificacionService, times(1)).delete(1L);
    }
}