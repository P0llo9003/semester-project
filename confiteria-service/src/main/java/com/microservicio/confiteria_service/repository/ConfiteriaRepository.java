package com.microservicio.confiteria_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.confiteria_service.model.Confiteria;

@Repository
public interface ConfiteriaRepository extends JpaRepository<Confiteria, Long>{
    
}
