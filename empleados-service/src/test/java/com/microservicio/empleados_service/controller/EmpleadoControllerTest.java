package com.microservicio.empleados_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.empleados_service.dto.EmpleadoDTO;
import com.microservicio.empleados_service.model.Empleado;
import com.microservicio.empleados_service.service.EmpleadoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class EmpleadoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmpleadoService empleadoService;

    private ObjectMapper objectMapper;

    private Empleado empleado;
    private EmpleadoDTO empleadoDTO;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        empleado = new Empleado(
                1L,
                12345678,
                "9",
                "Carlos",
                "Soto",
                "carlos.soto@cine.cl",
                "Cajero",
                "Mañana");

        empleadoDTO = new EmpleadoDTO(
                1L,
                12345678,
                "9",
                "Carlos",
                "Soto",
                "carlos.soto@cine.cl",
                "Cajero",
                "Mañana");

        EmpleadoController controller =
                new EmpleadoController(empleadoService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void testListarEmpleados() throws Exception {

        when(empleadoService.findAll())
                .thenReturn(List.of(empleado));

        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].run").value(12345678))
                .andExpect(jsonPath("$[0].nombre").value("Carlos"))
                .andExpect(jsonPath("$[0].cargo").value("Cajero"));
    }

    @Test
    void testBuscarPorId() throws Exception {

        when(empleadoService.findById(1L))
                .thenReturn(empleado);

        mockMvc.perform(get("/empleados/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.run").value(12345678))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.cargo").value("Cajero"));
    }

    @Test
    void testBuscarPorRun() throws Exception {

        when(empleadoService.findByRun(12345678))
                .thenReturn(empleado);

        mockMvc.perform(get("/empleados/run/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.run").value(12345678))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.cargo").value("Cajero"));
    }

    @Test
    void testCrearEmpleado() throws Exception {

        when(empleadoService.save(any(Empleado.class)))
                .thenReturn(empleado);

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleadoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.run").value(12345678))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.cargo").value("Cajero"));
    }

    @Test
    void testActualizarEmpleado() throws Exception {

        when(empleadoService.update(eq(1L), any(Empleado.class)))
                .thenReturn(empleado);

        mockMvc.perform(put("/empleados/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.run").value(12345678))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.cargo").value("Cajero"));
    }

    @Test
    void testEliminarEmpleado() throws Exception {

        when(empleadoService.findById(1L))
                .thenReturn(empleado);

        doNothing().when(empleadoService).delete(1L);

        mockMvc.perform(delete("/empleados/1"))
                .andExpect(status().isNoContent());

        verify(empleadoService, times(1)).delete(1L);
    }

    @Test
    void testExisteEmpleado() throws Exception {

        when(empleadoService.existById(1L))
                .thenReturn(true);

        mockMvc.perform(get("/empleados/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}