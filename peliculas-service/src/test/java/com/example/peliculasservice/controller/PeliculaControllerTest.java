package com.example.peliculasservice.controller;

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

import com.example.peliculasservice.dto.PeliculaDTO;
import com.example.peliculasservice.model.Pelicula;
import com.example.peliculasservice.service.PeliculaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class PeliculaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PeliculaService peliculaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Pelicula pelicula;
    private PeliculaDTO peliculaDto;

    @BeforeEach
    void setUp() {

        pelicula = new Pelicula(
                1L,
                "Avatar",
                "Pelicula de ciencia ficcion",
                180,
                "Accion",
                "+14"
        );

        peliculaDto = new PeliculaDTO(
                1L,
                "Avatar",
                "Pelicula de ciencia ficcion",
                180,
                "Accion",
                "+14"
        );

        PeliculaController controller = new PeliculaController(peliculaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListarPeliculas() throws Exception {

        when(peliculaService.findAll()).thenReturn(List.of(pelicula));

        mockMvc.perform(get("/peliculas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Avatar"))
                .andExpect(jsonPath("$[0].descripcion").value("Pelicula de ciencia ficcion"))
                .andExpect(jsonPath("$[0].duracion").value(180))
                .andExpect(jsonPath("$[0].genero").value("Accion"))
                .andExpect(jsonPath("$[0].clasificacion").value("+14"));
    }

    @Test
    public void testObtenerPelicula() throws Exception {

        when(peliculaService.findById(1L)).thenReturn(pelicula);

        mockMvc.perform(get("/peliculas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Avatar"))
                .andExpect(jsonPath("$.descripcion").value("Pelicula de ciencia ficcion"))
                .andExpect(jsonPath("$.duracion").value(180))
                .andExpect(jsonPath("$.genero").value("Accion"))
                .andExpect(jsonPath("$.clasificacion").value("+14"));
    }

    @Test
    public void testCrearPelicula() throws Exception {

        when(peliculaService.save(any(Pelicula.class))).thenReturn(pelicula);

        mockMvc.perform(post("/peliculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(peliculaDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Avatar"))
                .andExpect(jsonPath("$.descripcion").value("Pelicula de ciencia ficcion"))
                .andExpect(jsonPath("$.duracion").value(180))
                .andExpect(jsonPath("$.genero").value("Accion"))
                .andExpect(jsonPath("$.clasificacion").value("+14"));
    }

    @Test
    public void testActualizarPelicula() throws Exception {

        when(peliculaService.update(eq(1L), any(Pelicula.class)))
                .thenReturn(pelicula);

        mockMvc.perform(put("/peliculas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pelicula)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Avatar"))
                .andExpect(jsonPath("$.descripcion").value("Pelicula de ciencia ficcion"))
                .andExpect(jsonPath("$.duracion").value(180))
                .andExpect(jsonPath("$.genero").value("Accion"))
                .andExpect(jsonPath("$.clasificacion").value("+14"));
    }

    @Test
    public void testEliminarPelicula() throws Exception {

        when(peliculaService.findById(1L)).thenReturn(pelicula);
        doNothing().when(peliculaService).delete(1L);

        mockMvc.perform(delete("/peliculas/1"))
                .andExpect(status().isNoContent());

        verify(peliculaService, times(1)).delete(1L);
    }

    @Test
    public void testExistePelicula() throws Exception {

        when(peliculaService.existById(1L)).thenReturn(true);

        mockMvc.perform(get("/peliculas/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}