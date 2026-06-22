package com.microservicio.cines_service.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.cines_service.dto.CineDTO;
import com.microservicio.cines_service.model.Cine;
import com.microservicio.cines_service.service.CineService;

@ExtendWith(MockitoExtension.class)
public class CineControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CineService cineService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Cine cine;
    private CineDTO cineDto;

    @BeforeEach
    void setUp() {

        cine = new Cine(
                1L,
                "Cineplanet",
                "Av. Principal 123",
                "Santiago",
                123456789
        );

        cineDto = new CineDTO(
                1L,
                "Cineplanet",
                "Av. Principal 123",
                "Santiago",
                123456789
        );

        CineController controller = new CineController(cineService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListarCines() throws Exception {

        when(cineService.findAll()).thenReturn(List.of(cine));

        mockMvc.perform(get("/cines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Cineplanet"))
                .andExpect(jsonPath("$[0].direccion").value("Av. Principal 123"))
                .andExpect(jsonPath("$[0].ciudad").value("Santiago"))
                .andExpect(jsonPath("$[0].telefono").value(123456789));
    }

    @Test
    public void testObtenerCine() throws Exception {

        when(cineService.findById(1L)).thenReturn(cine);

        mockMvc.perform(get("/cines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Cineplanet"))
                .andExpect(jsonPath("$.direccion").value("Av. Principal 123"))
                .andExpect(jsonPath("$.ciudad").value("Santiago"))
                .andExpect(jsonPath("$.telefono").value(123456789));
    }

    @Test
    public void testObtenerCinePorTelefono() throws Exception {

        when(cineService.findByTelefono(123456789)).thenReturn(cine);

        mockMvc.perform(get("/cines/telefono/123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Cineplanet"))
                .andExpect(jsonPath("$.direccion").value("Av. Principal 123"))
                .andExpect(jsonPath("$.ciudad").value("Santiago"))
                .andExpect(jsonPath("$.telefono").value(123456789));
    }

    @Test
    public void testCrearCine() throws Exception {

        when(cineService.save(any(Cine.class))).thenReturn(cine);

        mockMvc.perform(post("/cines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cineDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Cineplanet"))
                .andExpect(jsonPath("$.direccion").value("Av. Principal 123"))
                .andExpect(jsonPath("$.ciudad").value("Santiago"))
                .andExpect(jsonPath("$.telefono").value(123456789));
    }

    @Test
    public void testActualizarCine() throws Exception {

        when(cineService.update(eq(1L), any(Cine.class))).thenReturn(cine);

        mockMvc.perform(put("/cines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Cineplanet"))
                .andExpect(jsonPath("$.direccion").value("Av. Principal 123"))
                .andExpect(jsonPath("$.ciudad").value("Santiago"))
                .andExpect(jsonPath("$.telefono").value(123456789));
    }

    @Test
    public void testEliminarCine() throws Exception {

        when(cineService.findById(1L)).thenReturn(cine);
        doNothing().when(cineService).delete(1L);

        mockMvc.perform(delete("/cines/1"))
                .andExpect(status().isNoContent());

        verify(cineService, times(1)).delete(1L);
    }

    @Test
    public void testExisteCine() throws Exception {

        when(cineService.existById(1L)).thenReturn(true);

        mockMvc.perform(get("/cines/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}