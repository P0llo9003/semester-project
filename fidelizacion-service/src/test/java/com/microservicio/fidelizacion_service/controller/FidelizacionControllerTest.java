package com.microservicio.fidelizacion_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.fidelizacion_service.dto.FidelizacionDTO;
import com.microservicio.fidelizacion_service.model.Fidelizacion;
import com.microservicio.fidelizacion_service.service.fidelizacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class FidelizacionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private fidelizacionService fidelizacionService;

    private ObjectMapper objectMapper;

    private Fidelizacion fidelizacion;
    private FidelizacionDTO fidelizacionDTO;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        fidelizacion =
                new Fidelizacion(
                        1L,
                        1L,
                        100,
                        "Bronce");

        fidelizacionDTO =
                new FidelizacionDTO(
                        1L,
                        1L,
                        100,
                        "Bronce");

        FidelizacionController controller =
                new FidelizacionController(fidelizacionService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void testListarFidelizaciones() throws Exception {

        when(fidelizacionService.findAll())
                .thenReturn(List.of(fidelizacion));

        mockMvc.perform(get("/fidelizaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].usuarioId").value(1L))
                .andExpect(jsonPath("$[0].puntos").value(100))
                .andExpect(jsonPath("$[0].nivel").value("Bronce"));
    }

    @Test
    void testBuscarPorId() throws Exception {

        when(fidelizacionService.findById(1L))
                .thenReturn(fidelizacion);

        mockMvc.perform(get("/fidelizaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.puntos").value(100))
                .andExpect(jsonPath("$.nivel").value("Bronce"));
    }

    @Test
    void testCrearFidelizacion() throws Exception {

        when(fidelizacionService.save(any(Fidelizacion.class)))
                .thenReturn(fidelizacion);

        mockMvc.perform(post("/fidelizaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fidelizacionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.puntos").value(100))
                .andExpect(jsonPath("$.nivel").value("Bronce"));
    }

    @Test
    void testActualizarFidelizacion() throws Exception {

        when(fidelizacionService.update(eq(1L), any(Fidelizacion.class)))
                .thenReturn(fidelizacion);

        mockMvc.perform(put("/fidelizaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fidelizacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.puntos").value(100))
                .andExpect(jsonPath("$.nivel").value("Bronce"));
    }

    @Test
    void testEliminarFidelizacion() throws Exception {

        when(fidelizacionService.findById(1L))
                .thenReturn(fidelizacion);

        doNothing().when(fidelizacionService).delete(1L);

        mockMvc.perform(delete("/fidelizaciones/1"))
                .andExpect(status().isNoContent());

        verify(fidelizacionService, times(1)).delete(1L);
    }

    @Test
    void testExisteFidelizacion() throws Exception {

        when(fidelizacionService.existById(1L))
                .thenReturn(true);

        mockMvc.perform(get("/fidelizaciones/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}