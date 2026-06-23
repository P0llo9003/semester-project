package com.microservicio.confiteria_service.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.confiteria_service.model.Confiteria;
import com.microservicio.confiteria_service.repository.ConfiteriaRepository;

@ExtendWith(MockitoExtension.class)
class ConfiteriaServiceTest {

    private ConfiteriaService confiteriaService;

    @Mock
    private ConfiteriaRepository confiteriaRepository;

    @BeforeEach
    void setUp() {
        confiteriaService = new ConfiteriaService(confiteriaRepository);
    }

    @Test
    void testSave() {

        Confiteria confiteria = new Confiteria(
                1L,
                "Palomitas",
                "Palomitas grandes",
                5000,
                true);

        when(confiteriaRepository.save(any(Confiteria.class)))
                .thenReturn(confiteria);

        Confiteria resultado = confiteriaService.save(confiteria);

        assertNotNull(resultado);
        assertEquals("Palomitas", resultado.getNombre());

        verify(confiteriaRepository).save(any(Confiteria.class));
    }

    @Test
    void testFindAll() {

        when(confiteriaRepository.findAll())
                .thenReturn(List.of(
                        new Confiteria(
                                1L,
                                "Bebida",
                                "Coca Cola",
                                3000,
                                true)));

        List<Confiteria> productos = confiteriaService.findAll();

        assertNotNull(productos);
        assertEquals(1, productos.size());

        verify(confiteriaRepository).findAll();
    }

    @Test
    void testFindById() {

        Long id = 1L;

        Confiteria confiteria = new Confiteria(
                id,
                "Nachos",
                "Nachos medianos",
                4500,
                true);

        when(confiteriaRepository.findById(id))
                .thenReturn(Optional.of(confiteria));

        Confiteria resultado = confiteriaService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(confiteriaRepository).findById(id);
    }

    @Test
    void testUpdate() {

        Long id = 1L;

        Confiteria existente = new Confiteria(
                id,
                "Palomitas",
                "Pequeñas",
                3000,
                true);

        when(confiteriaRepository.findById(id))
                .thenReturn(Optional.of(existente));

        Confiteria cambios = new Confiteria(
                id,
                "Palomitas XL",
                "Grandes",
                6000,
                false);

        Confiteria guardada = new Confiteria(
                id,
                "Palomitas XL",
                "Grandes",
                6000,
                false);

        when(confiteriaRepository.save(any(Confiteria.class)))
                .thenReturn(guardada);

        Confiteria resultado = confiteriaService.update(id, cambios);

        assertNotNull(resultado);
        assertEquals("Palomitas XL", resultado.getNombre());
        assertEquals(6000, resultado.getPrecio());
        assertFalse(resultado.isDisponibilidad());

        verify(confiteriaRepository).findById(id);
        verify(confiteriaRepository).save(any(Confiteria.class));
    }

    @Test
    void testDelete() {

        Long id = 1L;

        confiteriaService.delete(id);

        verify(confiteriaRepository).deleteById(id);
    }
}