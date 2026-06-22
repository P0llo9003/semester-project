package com.microservicio.cines_service.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.cines_service.model.Cine;
import com.microservicio.cines_service.repository.CineRepository;

@ExtendWith(MockitoExtension.class)
class CineServiceTest {

    private CineService cineService;

    @Mock
    private CineRepository cineRepository;

    @BeforeEach
    void setUp() {
        cineService = new CineService(cineRepository);
    }

    @Test
    void testSave() {

        Cine cine = new Cine(
                1L,
                "Cineplanet",
                "Av. Principal 123",
                "Santiago",
                123456789
        );

        when(cineRepository.save(any(Cine.class))).thenReturn(cine);

        Cine resultado = cineService.save(cine);

        assertNotNull(resultado);
        assertEquals("Cineplanet", resultado.getNombre());

        verify(cineRepository).save(any(Cine.class));
    }

    @Test
    void testFindAll() {

        when(cineRepository.findAll()).thenReturn(
                List.of(
                        new Cine(
                                1L,
                                "Cineplanet",
                                "Direccion",
                                "Santiago",
                                123456789
                        )
                )
        );

        List<Cine> cines = cineService.findAll();

        assertNotNull(cines);
        assertEquals(1, cines.size());

        verify(cineRepository).findAll();
    }

    @Test
    void testFindById() {

        Long id = 1L;

        Cine cine = new Cine(
                id,
                "Cineplanet",
                "Direccion",
                "Santiago",
                123456789
        );

        when(cineRepository.findById(id))
                .thenReturn(Optional.of(cine));

        Cine resultado = cineService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(cineRepository).findById(id);
    }

    @Test
    void testFindByTelefono() {

        int telefono = 123456789;

        Cine cine = new Cine(
                1L,
                "Cineplanet",
                "Direccion",
                "Santiago",
                telefono
        );

        when(cineRepository.findByTelefono(telefono))
                .thenReturn(Optional.of(cine));

        Cine resultado = cineService.findByTelefono(telefono);

        assertNotNull(resultado);
        assertEquals(telefono, resultado.getTelefono());

        verify(cineRepository).findByTelefono(telefono);
    }

    @Test
    void testUpdate() {

        Long id = 1L;

        Cine existente = new Cine(
                id,
                "Cine Viejo",
                "Direccion Vieja",
                "Valparaiso",
                111111111
        );

        Cine cambios = new Cine(
                id,
                "Cine Nuevo",
                "Direccion Nueva",
                "Santiago",
                222222222
        );

        when(cineRepository.findById(id))
                .thenReturn(Optional.of(existente));

        when(cineRepository.save(any(Cine.class)))
                .thenReturn(cambios);

        Cine resultado = cineService.update(id, cambios);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Cine Nuevo", resultado.getNombre());
        assertEquals("Direccion Nueva", resultado.getDireccion());
        assertEquals("Santiago", resultado.getCiudad());

        verify(cineRepository).findById(id);
        verify(cineRepository).save(any(Cine.class));
    }

    @Test
    void testDelete() {

        Long id = 1L;

        when(cineRepository.existsById(id))
                .thenReturn(true);

        cineService.delete(id);

        verify(cineRepository).deleteById(id);
    }

    @Test
    void testExistById() {

        Long id = 1L;

        when(cineRepository.existsById(id))
                .thenReturn(true);

        boolean existe = cineService.existById(id);

        assertTrue(existe);

        verify(cineRepository).existsById(id);
    }
}