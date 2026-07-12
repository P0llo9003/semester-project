package com.microservicio.empleados_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservicio.empleados_service.model.Empleado;
import com.microservicio.empleados_service.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmpleadoService.class);

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado save(Empleado empleado) {

        logger.info("Guardando empleado {}", empleado.getNombre());

        Empleado empleadoGuardado =
                empleadoRepository.save(empleado);

        logger.info("Empleado guardado con id={}",
                empleadoGuardado.getId());

        return empleadoGuardado;
    }

    public List<Empleado> findAll() {

        logger.info("Listando todos los empleados");

        return empleadoRepository.findAll();
    }

    public Empleado findById(Long id) {

        logger.info("Buscando empleado id={}", id);

        return empleadoRepository.findById(id)
                .orElse(null);
    }

    public Empleado update(Long id, Empleado empleadoActualizado) {

        logger.info("Actualizando empleado id={}", id);

        Empleado empleado =
                empleadoRepository.findById(id)
                        .orElse(null);

        if (empleado == null) {
            logger.warn("Empleado {} no encontrado", id);
            return null;
        }

        empleado.setRun(empleadoActualizado.getRun());
        empleado.setDv(empleadoActualizado.getDv());
        empleado.setNombre(empleadoActualizado.getNombre());
        empleado.setApellido(empleadoActualizado.getApellido());
        empleado.setCorreo(empleadoActualizado.getCorreo());
        empleado.setCargo(empleadoActualizado.getCargo());
        empleado.setTurno(empleadoActualizado.getTurno());

        Empleado actualizado =
                empleadoRepository.save(empleado);

        logger.info("Empleado {} actualizado correctamente", id);

        return actualizado;
    }

    public void delete(Long id) {

        logger.info("Eliminando empleado id={}", id);

        empleadoRepository.deleteById(id);

        logger.info("Empleado {} eliminado", id);
    }

    public boolean existById(Long id) {

        return empleadoRepository.existsById(id);
    }

    public Empleado findByRun(int run) {

        logger.info("Buscando empleado por RUN={}", run);

        return empleadoRepository.findByRun(run);
    }
}