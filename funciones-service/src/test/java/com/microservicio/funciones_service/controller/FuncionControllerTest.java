package com.microservicio.funciones_service.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
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
import com.microservicio.funciones_service.dto.FuncionDTO;
import com.microservicio.funciones_service.model.Funcion;
import com.microservicio.funciones_service.service.FuncionService;

@ExtendWith(MockitoExtension.class)
public class FuncionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FuncionService funcionService;

    private final ObjectMapper objectMapper =
            new ObjectMapper().findAndRegisterModules();

    private Funcion funcion;
    private FuncionDTO funcionDTO;

    @BeforeEach
    void setUp() {

        funcion =
                new Funcion(
                        1L,
                        1L,
                        1L,
                        LocalDate.of(2026, 6, 22),
                        LocalTime.of(20, 0),
                        "ACTIVA");

        funcionDTO = FuncionDTO.fromModel(funcion);

        FuncionController controller =
                new FuncionController(funcionService);

        mockMvc =
                MockMvcBuilders.standaloneSetup(controller)
                        .build();
    }

    @Test
    void testFindAll() throws Exception {

        when(funcionService.findAll())
                .thenReturn(List.of(funcion));

        mockMvc.perform(get("/funciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].peliculaId").value(1))
                .andExpect(jsonPath("$[0].salaId").value(1));
    }

    @Test
    void testFindById() throws Exception {

        when(funcionService.findById(1L))
                .thenReturn(funcion);

        mockMvc.perform(get("/funciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {

        when(funcionService.save(any(Funcion.class)))
                .thenReturn(funcion);

        mockMvc.perform(post("/funciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ACTIVA"));
    }

    @Test
    void testUpdate() throws Exception {

        when(funcionService.update(eq(1L), any(Funcion.class)))
                .thenReturn(funcion);

        mockMvc.perform(put("/funciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDelete() throws Exception {

        when(funcionService.findById(1L))
                .thenReturn(funcion);

        doNothing().when(funcionService).delete(1L);

        mockMvc.perform(delete("/funciones/1"))
                .andExpect(status().isNoContent());

        verify(funcionService).delete(1L);
    }

    @Test
    void testExistsById() throws Exception {

        when(funcionService.existsById(1L))
                .thenReturn(true);

        mockMvc.perform(get("/funciones/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}