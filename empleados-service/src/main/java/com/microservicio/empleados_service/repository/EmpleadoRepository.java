package com.microservicio.empleados_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.empleados_service.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Empleado findByRun(int run);

}