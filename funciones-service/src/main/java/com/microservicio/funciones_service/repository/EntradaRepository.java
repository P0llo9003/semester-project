package com.microservicio.funciones_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.funciones_service.model.Entrada;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    
}
