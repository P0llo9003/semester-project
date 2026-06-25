package com.microservicio.funciones_service.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @NotNull(message = "El Id de la pelicula es obligatorio")
   @Column(nullable = false)    
    private Long peliculaId;
   
   @NotNull(message = "El Id de la sala es obligatorio")
   @Column(nullable = false)    
    private Long salaId;

   @Column(nullable = false)    
    private LocalDate fecha;

   @Column(nullable = false)    
    private LocalTime hora;

   @NotBlank(message = "El estado es obligatorio")
   @Column(nullable = false)    
    private String estado;
}