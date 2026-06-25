package com.microservicio.pago_service.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.pago_service.model.Pago;
import com.microservicio.pago_service.repository.PagoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {
    private PagoService pagoService;
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() throws Exception {
        pagoService = new PagoService(pagoRepository, webClient);
        Field field = PagoService.class.getDeclaredField("entradaPath");
        field.setAccessible(true);
        field.set(pagoService, "http://api/entrada/%d");
    }

    private void mockEntradaExistente() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenReturn(Mono.just(Boolean.TRUE));
    }


    @Test
    void testGuardar() {
        mockEntradaExistente();
        // create Pago with fields required by the model (IVA and total will be set by the service)
        Pago pago = new Pago(1L, 1L, 1000, 0, 10, 0, "Tarjeta", new Date());
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);
        Pago resultado = pagoService.save(pago);
        assertNotNull(resultado);
        assertEquals("Tarjeta", resultado.getMedioPago());
        verify(pagoRepository).save(any(Pago.class));
    }
    @Test
    void testListar() {
        // subtotal=5000, iva=5000*19/100=950, total=5950
        when(pagoRepository.findAll()).thenReturn(List.of(new Pago(1L, 1L, 5000, 950, 0, 5950, "Tarjeta", new Date())));
        List<Pago> pagos = pagoService.findAll();
        assertNotNull(pagos);
        assertEquals(1, pagos.size());
        verify(pagoRepository).findAll();
    }
    @Test
    void testObtenerPorId() {
        Long id = 1L;
        Pago pago = new Pago(id, 1L, 1000, 190, 10, 1090, "Tarjeta", new Date());
        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));
        Pago resultado = pagoService.findById(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(pagoRepository).findById(id);
    }

    @Test
    void testActualizar() {
        mockEntradaExistente();
        Long id = 1L;
        Pago existente = new Pago(id, 1L, 1000, 190, 10, 1090, "Tarjeta", new Date());
        when(pagoRepository.findById(id)).thenReturn(Optional.of(existente));

        // cambios: nuevo valor neto 2000, descuento 0 -> subtotal 2000, iva 380, total 2380
        Pago cambios = new Pago(id, 2L, 2000, 0, 0, 0, "Efectivo", new Date());
        Pago guardado = new Pago(id, 2L, 2000, 380, 0, 2380, "Efectivo", cambios.getFecha());
        when(pagoRepository.save(any(Pago.class))).thenReturn(guardado);

        Pago resultado = pagoService.update(id, cambios);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(2L, resultado.getIdEntrada());
        assertEquals(2000, resultado.getValorNeto());
        assertEquals(2380, resultado.getTotalPagar());
        assertEquals("Efectivo", resultado.getMedioPago());

        verify(pagoRepository).findById(id);
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        when(pagoRepository.existsById(id)).thenReturn(true);
        pagoService.delete(id);
        verify(pagoRepository).deleteById(id);
    }

    @Test
    void testCalcularSubtotal() {
        // caso válido
        int subtotal = pagoService.calcularSubtotal(1000, 10); // 1000 - 10% = 900
        assertEquals(900, subtotal);

        // casos inválidos
        assertThrows(IllegalArgumentException.class, () -> pagoService.calcularSubtotal(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> pagoService.calcularSubtotal(1000, -5));
        assertThrows(IllegalArgumentException.class, () -> pagoService.calcularSubtotal(1000, 101));
    }

    @Test
    void testCalcularIVA() {
        // caso válido
        int iva = pagoService.calcularIVA(1000); // 19% de 1000 = 190
        assertEquals(190, iva);
        // caso inválido
        assertThrows(IllegalArgumentException.class, () -> pagoService.calcularIVA(-50));
    }
}