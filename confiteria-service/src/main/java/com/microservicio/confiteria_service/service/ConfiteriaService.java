package com.microservicio.confiteria_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservicio.confiteria_service.exception.ResourceNotFoundException;
import com.microservicio.confiteria_service.model.Confiteria;
import com.microservicio.confiteria_service.repository.ConfiteriaRepository;

@Service
public class ConfiteriaService {

    private final ConfiteriaRepository confiteriaRepository;
    private static final Logger logger = LoggerFactory.getLogger(ConfiteriaService.class);

    public ConfiteriaService(ConfiteriaRepository confiteriaRepository) {
        this.confiteriaRepository = confiteriaRepository;
    }

    
    public Confiteria save(Confiteria confiteria) {
        logger.info("Iniciando guardado de producto de confiteria con id={}, Nombre={}, precio={}",
                    confiteria.getId(), confiteria.getNombre(), confiteria.getPrecio()
        );
        return confiteriaRepository.save(confiteria);
    }


    public List<Confiteria> findAll() {
        logger.info("Listando todos los productos de confiteria");
        List<Confiteria> confiteria = confiteriaRepository.findAll();
        logger.info("Total de productos encontrados: {}", confiteria.size());
        return confiteria;
    }


    public Confiteria findById(Long id) {
        logger.info("Buscando un producto por id={}", id);
        Confiteria confi = confiteriaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Producto no encontrado id={}", id);
                    return new ResourceNotFoundException("Producto no existe");
                       });
        logger.info("Producto encontrado id={}", id);
        return confi;
    }


    public Confiteria update(Long id, Confiteria updatedConfi) {
        logger.info("Actualizando producto id={}", id);

        Confiteria confi1 = confiteriaRepository.findById(id).orElse(null);

        if(confi1 != null) {
            confi1.setNombre(updatedConfi.getNombre());
            confi1.setDescripcion(updatedConfi.getDescripcion());
            confi1.setPrecio(updatedConfi.getPrecio());
            confi1.setDisponibilidad(updatedConfi.isDisponibilidad());

            logger.info("Producto actualizado id={}", id);
            return confiteriaRepository.save(confi1);
        }

        logger.warn("No se pudo actualizar producto id={}", id);
        return null;
    }


    public void delete(Long id) {
        logger.info("Eliminando producto id={}", id);

        confiteriaRepository.deleteById(id);

        logger.info("Producto eliminado con exito, id={}", id);
    }

}
