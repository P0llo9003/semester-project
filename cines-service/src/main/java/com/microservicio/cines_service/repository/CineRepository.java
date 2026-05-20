package com.microservicio.cines_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.cines_service.model.Cine;

@Repository
public interface CineRepository extends JpaRepository<Cine, Long>{
    
}
