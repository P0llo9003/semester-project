package com.microservicio.pago_service.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.pago_service.exception.BadRequestException;
import com.microservicio.pago_service.exception.ResourceNotFoundException;
import com.microservicio.pago_service.model.Pago;
import com.microservicio.pago_service.repository.PagoRepository;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);

    @Value("${api.entrada.exists}")
    private String entradaPath;

    public PagoService(PagoRepository pagoRepository,WebClient webClient) {
        this.pagoRepository = pagoRepository;
        this.webClient = webClient;
    }

    
    public Pago save(Pago pago) {
        logger.info("Iniciando guardar pago con idEntrada={}, Neto={}, Dcto{}, MedioPago={}", 
            pago.getIdEntrada(), pago.getValorNeto(), pago.getDescuento(),pago.getMedioPago());
            pago.setIva(calcularIVA(calcularSubtotal(pago.getValorNeto(),pago.getDescuento())));
            pago.setTotalPagar(calcularSubtotal(pago.getValorNeto(),pago.getDescuento())+pago.getIva());
        try {
            if (pago.getTotalPagar() <=0) throw new IllegalArgumentException("totalPagar requerido");
            if (pago.getMedioPago() == null || pago.getMedioPago().isBlank()) throw new IllegalArgumentException("medioPago requerido");
            if (pago.getFecha() == null) pago.setFecha(new Date());

            logger.info("ID ENTRADA = {}", pago.getIdEntrada());

            String uri = String.format(entradaPath, pago.getIdEntrada());
            logger.info("URI = {}", uri);

            logger.info("Realizando petición a api-gateway: {}", uri);
            Boolean existeEntrada = webClient.get()
                    .uri(String.format(entradaPath, pago.getIdEntrada()))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            logger.info("Respuesta de api-gateway: existeEntrada={}", existeEntrada);

            if (existeEntrada == null) {
                logger.error("No se pudo validar la existencia de la entrada");
                throw new BadRequestException("No se pudo validar la existencia de la entrada");
            }
            if (Boolean.FALSE.equals(existeEntrada)) {
                logger.warn("Entrada no existe con id={}", pago.getIdEntrada());
                throw new ResourceNotFoundException("Entrada no existe");
            }
            
            Pago pagoGuardado = pagoRepository.save(pago);
            logger.info("Pago guardado exitosamente con id={}", pagoGuardado.getId());
            return pagoGuardado;
        } catch (Exception e) {
            logger.error("Error al guardar pago: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Pago> findAll() {
        logger.info("Listando todos los pagos");
        List<Pago> pagos = pagoRepository.findAll();
        logger.info("Total pagos encontrados: {}", pagos.size());
        return pagos;
    }

    public Pago findById(Long id) {
        logger.info("Buscando pago por id={}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pago no encontrado id={}", id);
                    return new ResourceNotFoundException("Pago no existe");
                });
        logger.info("Pago encontrado id={}", id);
        return pago;
    }

    public Pago update(Long id, Pago pago) {
        logger.info("Iniciando actualizar pago id={}", id);
        try {
            Pago existente = pagoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Pago no existe"));

            if (pago.getValorNeto() <= 0) throw new IllegalArgumentException("Valor Neto requerido");
            if (pago.getMedioPago() == null || pago.getMedioPago().isBlank()) throw new IllegalArgumentException("Medio Pago Requerido");
            if (pago.getFecha() == null) pago.setFecha(new Date());

            logger.info("Validando existencia de entrada para pago idEntrada={}", pago.getIdEntrada());
            Boolean existeEntrada = webClient.get()
                    .uri(String.format(entradaPath, pago.getIdEntrada()))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            logger.info("Respuesta de validación entrada: {}", existeEntrada);

            if (existeEntrada == null) {
                logger.error("No se pudo validar la existencia de la entrada");
                throw new BadRequestException("No se pudo validar la existencia de la entrada");
            }
            if (Boolean.FALSE.equals(existeEntrada)) {
                logger.warn("Entrada no existe con id={}", pago.getIdEntrada());
                throw new ResourceNotFoundException("Entrada no existe");
            }

            existente.setIdEntrada(pago.getIdEntrada());

            int valorNeto = pago.getValorNeto();
            int descuento = pago.getDescuento();
            int subtotal = calcularSubtotal(valorNeto, descuento);
            int iva = calcularIVA(subtotal);
            int totalPagar = subtotal + iva;

            existente.setValorNeto(valorNeto);
            existente.setIva(iva);
            existente.setDescuento(descuento);
            existente.setTotalPagar(totalPagar);
            existente.setMedioPago(pago.getMedioPago());
            existente.setFecha(pago.getFecha());

            Pago actualizado = pagoRepository.save(existente);
            logger.info("Pago actualizado exitosamente id={}", actualizado.getId());
            return actualizado;
        } catch (Exception e) {
            logger.error("Error al actualizar pago id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public void delete(Long id) {
        logger.info("Iniciando eliminación de pago id={}", id);
        try {
            if (!pagoRepository.existsById(id)) {
                logger.warn("Pago no existe para eliminar id={}", id);
                throw new ResourceNotFoundException("Pago no existe");
            }
            pagoRepository.deleteById(id);
            logger.info("Pago eliminado exitosamente id={}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar pago id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public int calcularSubtotal(int neto, int porcentajeDescuento) {
        if (neto < 0) {
            throw new IllegalArgumentException("El valor neto no puede ser negativo");
        }
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }
        int subtotal = neto - (neto * porcentajeDescuento / 100);
        return subtotal;
    }

    public int calcularIVA(int subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("El subtotal no puede ser negativo");
        }
        int iva = subtotal * 19 / 100;
        return iva;
    }
}
