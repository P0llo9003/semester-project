package com.microservicio.fidelizacion_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.fidelizacion_service.model.Fidelizacion;

@Repository
public interface FidelizacionRepository extends JpaRepository<Fidelizacion, Long>{

    Fidelizacion findByUsuarioId(Long usuarioId);

}
