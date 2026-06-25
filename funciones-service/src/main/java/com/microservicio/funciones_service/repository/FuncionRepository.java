package com.microservicio.funciones_service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.funciones_service.model.Funcion;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Long>{

    List<Funcion> findByPeliculaId(Long peliculaId);

    List<Funcion> findBySalaId(Long salaId);

    List<Funcion> findByFecha(LocalDate fecha);

}
