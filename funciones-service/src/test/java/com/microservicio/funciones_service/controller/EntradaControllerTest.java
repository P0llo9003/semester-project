package com.microservicio.funciones_service.controller;

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
import com.microservicio.funciones_service.dto.EntradaDTO;
import com.microservicio.funciones_service.model.Entrada;
import com.microservicio.funciones_service.service.EntradaService;

@ExtendWith(MockitoExtension.class)
public class EntradaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EntradaService entradaService;

    private ObjectMapper objectMapper =
            new ObjectMapper();

    private Entrada entrada;
    private EntradaDTO entradaDTO;

    @BeforeEach
    void setUp() {

        entrada = new Entrada(
                1L,
                1L,
                1L,
                "A1");

        entradaDTO = new EntradaDTO(
                null,
                1L,
                1L,
                "A1");

        EntradaController controller =
                new EntradaController(entradaService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void testFindAll() throws Exception {

        when(entradaService.findAll())
                .thenReturn(List.of(entrada));

        mockMvc.perform(get("/entradas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].funcionId").value(1))
                .andExpect(jsonPath("$[0].usuarioId").value(1))
                .andExpect(jsonPath("$[0].asiento").value("A1"));
    }

    @Test
    public void testFindById() throws Exception {

        when(entradaService.findById(1L))
                .thenReturn(entrada);

        mockMvc.perform(get("/entradas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.funcionId").value(1))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.asiento").value("A1"));
    }

    @Test
    public void testSave() throws Exception {

        when(entradaService.save(any(Entrada.class)))
                .thenReturn(entrada);

        mockMvc.perform(post("/entradas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entradaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.funcionId").value(1))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.asiento").value("A1"));
    }

    @Test
    public void testUpdate() throws Exception {

        when(entradaService.update(eq(1L), any(Entrada.class)))
                .thenReturn(entrada);

        mockMvc.perform(put("/entradas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entradaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.funcionId").value(1))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.asiento").value("A1"));
    }

    @Test
    public void testDelete() throws Exception {

        when(entradaService.findById(1L))
                .thenReturn(entrada);

        doNothing().when(entradaService)
                .delete(1L);

        mockMvc.perform(delete("/entradas/1"))
                .andExpect(status().isNoContent());

        verify(entradaService, times(1))
                .delete(1L);
    }

    @Test
    void testTotalEntradas() throws Exception {

    when(entradaService.totalEntradas())
            .thenReturn(15L);

    mockMvc.perform(get("/entradas/total"))
            .andExpect(status().isOk())
            .andExpect(content().string("15"));
    }


    @Test
    void testExistsById() throws Exception {

        when(entradaService.existsById(1L))
                .thenReturn(true);

        mockMvc.perform(get("/entradas/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }    
}