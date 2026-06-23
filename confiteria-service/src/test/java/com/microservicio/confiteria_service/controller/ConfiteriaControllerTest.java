package com.microservicio.confiteria_service.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.confiteria_service.dto.ConfiteriaDTO;
import com.microservicio.confiteria_service.model.Confiteria;
import com.microservicio.confiteria_service.service.ConfiteriaService;

@ExtendWith(MockitoExtension.class)
public class ConfiteriaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConfiteriaService confiteriaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Confiteria confiteria;
    private ConfiteriaDTO confiteriaDTO;

    @BeforeEach
    void setUp() {

        confiteria = new Confiteria(
                1L,
                "Palomitas",
                "Palomitas grandes",
                5000,
                true);

        confiteriaDTO = new ConfiteriaDTO(
                null,
                "Palomitas",
                "Palomitas grandes",
                5000,
                true);

        ConfiteriaController controller =
                new ConfiteriaController(confiteriaService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void testListarProductos() throws Exception {

        when(confiteriaService.findAll())
                .thenReturn(List.of(confiteria));

        mockMvc.perform(get("/confiteria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Palomitas"))
                .andExpect(jsonPath("$[0].descripcion").value("Palomitas grandes"))
                .andExpect(jsonPath("$[0].precio").value(5000))
                .andExpect(jsonPath("$[0].disponibilidad").value(true));
    }

    @Test
    public void testFindById() throws Exception {

        when(confiteriaService.findById(1L))
                .thenReturn(confiteria);

        mockMvc.perform(get("/confiteria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Palomitas"))
                .andExpect(jsonPath("$.descripcion").value("Palomitas grandes"))
                .andExpect(jsonPath("$.precio").value(5000))
                .andExpect(jsonPath("$.disponibilidad").value(true));
    }

    @Test
    public void testCrearProducto() throws Exception {

        when(confiteriaService.save(any(Confiteria.class)))
                .thenReturn(confiteria);

        mockMvc.perform(post("/confiteria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(confiteriaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Palomitas"))
                .andExpect(jsonPath("$.descripcion").value("Palomitas grandes"))
                .andExpect(jsonPath("$.precio").value(5000))
                .andExpect(jsonPath("$.disponibilidad").value(true));
    }

    @Test
    public void testActualizarProducto() throws Exception {

        when(confiteriaService.update(eq(1L), any(Confiteria.class)))
                .thenReturn(confiteria);

        mockMvc.perform(put("/confiteria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(confiteriaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Palomitas"))
                .andExpect(jsonPath("$.descripcion").value("Palomitas grandes"))
                .andExpect(jsonPath("$.precio").value(5000))
                .andExpect(jsonPath("$.disponibilidad").value(true));
    }

    @Test
    public void testEliminarProducto() throws Exception {

        when(confiteriaService.findById(1L))
                .thenReturn(confiteria);

        doNothing().when(confiteriaService).delete(1L);

        mockMvc.perform(delete("/confiteria/1"))
                .andExpect(status().isNoContent());

        verify(confiteriaService, times(1))
                .delete(1L);
    }
}