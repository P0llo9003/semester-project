package com.microservicio.empleados_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.microservicio.empleados_service.model.Empleado;
import com.microservicio.empleados_service.repository.EmpleadoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    private EmpleadoService empleadoService;

    @Mock
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleadoService = new EmpleadoService(empleadoRepository);

        empleado = new Empleado(
                1L,
                12345678,
                "9",
                "Carlos",
                "Soto",
                "carlos.soto@cine.cl",
                "Cajero",
                "Mañana");
    }

    @Test
    void testGuardar() {

        when(empleadoRepository.save(any(Empleado.class)))
                .thenReturn(empleado);

        Empleado resultado =
                empleadoService.save(empleado);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("Cajero", resultado.getCargo());

        verify(empleadoRepository).save(any(Empleado.class));
    }

    @Test
    void testListar() {

        when(empleadoRepository.findAll())
                .thenReturn(List.of(empleado));

        List<Empleado> empleados =
                empleadoService.findAll();

        assertNotNull(empleados);
        assertEquals(1, empleados.size());

        verify(empleadoRepository).findAll();
    }

    @Test
    void testBuscarPorId() {

        when(empleadoRepository.findById(1L))
                .thenReturn(Optional.of(empleado));

        Empleado resultado =
                empleadoService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(empleadoRepository).findById(1L);
    }
}