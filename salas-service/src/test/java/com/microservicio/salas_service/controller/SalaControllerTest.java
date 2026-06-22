package com.microservicio.salas_service.controller;

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
import com.microservicio.salas_service.model.Sala;
import com.microservicio.salas_service.service.SalasService;

@ExtendWith(MockitoExtension.class)
public class SalaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SalasService salasService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Sala sala;

    @BeforeEach
    void setUp() {

        sala = new Sala(
                1L,
                "Sala IMAX",
                120,
                "IMAX",
                1L);

        SalaController controller = new SalaController(salasService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void testFindAll() throws Exception {

        when(salasService.findAll())
                .thenReturn(List.of(sala));

        mockMvc.perform(get("/salas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Sala IMAX"))
                .andExpect(jsonPath("$[0].capacidad").value(120))
                .andExpect(jsonPath("$[0].tipo").value("IMAX"));
    }

    @Test
    void testFindById() throws Exception {

        when(salasService.findById(1L))
                .thenReturn(sala);

        mockMvc.perform(get("/salas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Sala IMAX"));
    }

    @Test
    void testSave() throws Exception {

        when(salasService.save(any(Sala.class)))
                .thenReturn(sala);

        mockMvc.perform(post("/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sala)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Sala IMAX"));
    }

    @Test
    void testUpdate() throws Exception {

        when(salasService.update(eq(1L), any(Sala.class)))
                .thenReturn(sala);

        mockMvc.perform(put("/salas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sala)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Sala IMAX"));
    }

    @Test
    void testDelete() throws Exception {

        when(salasService.findById(1L))
                .thenReturn(sala);

        doNothing().when(salasService).delete(1L);

        mockMvc.perform(delete("/salas/1"))
                .andExpect(status().isNoContent());

        verify(salasService, times(1))
                .delete(1L);
    }

    @Test
    void testExistById() throws Exception {

        when(salasService.existById(1L))
                .thenReturn(true);

        mockMvc.perform(get("/salas/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}