package com.microservicio.usuarios_service.service;

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

import com.microservicio.usuarios_service.model.Usuario;
import com.microservicio.usuarios_service.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(
                List.of(
                        new Usuario(
                                1L,
                                12345678,
                                "K",
                                "Juan",
                                "Perez",
                                "juan@correo.cl")));

        List<Usuario> usuarios = usuarioService.findAll();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());

        verify(usuarioRepository).findAll();
    }

    @Test
    void testFindById() {

        Long id = 1L;

        Usuario usuario = new Usuario(
                id,
                12345678,
                "K",
                "Juan",
                "Perez",
                "juan@correo.cl");

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(usuarioRepository).findById(id);
    }

    @Test
    void testSave() {

        Usuario usuario = new Usuario(
                1L,
                12345678,
                "K",
                "Juan",
                "Perez",
                "juan@correo.cl");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);

        Usuario resultado = usuarioService.save(usuario);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testUpdate() {

        Long id = 1L;

        Usuario existente = new Usuario(
                id,
                12345678,
                "K",
                "Juan",
                "Perez",
                "juan@correo.cl");

        Usuario cambios = new Usuario(
                id,
                87654321,
                "9",
                "Pedro",
                "Gonzalez",
                "pedro@correo.cl");

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(existente));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(cambios);

        Usuario resultado = usuarioService.update(id, cambios);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNombre());
        assertEquals("Gonzalez", resultado.getApellido());

        verify(usuarioRepository).findById(id);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testDelete() {

        Long id = 1L;

        Usuario usuario = new Usuario(
                id,
                12345678,
                "K",
                "Juan",
                "Perez",
                "juan@correo.cl");

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuario));

        usuarioService.delete(id);

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void testExistePorId() {

        Long id = 1L;

        when(usuarioRepository.existsById(id))
                .thenReturn(true);

        boolean existe = usuarioService.existePorId(id);

        assertTrue(existe);

        verify(usuarioRepository).existsById(id);
    }
}