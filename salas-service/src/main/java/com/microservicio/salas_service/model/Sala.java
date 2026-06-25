package com.microservicio.salas_service.model;

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
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
   @NotBlank(message = "El nombre de la sala es obligatorio")
   @Column(nullable = false) 
    private String nombre;

   @NotNull(message = "La capacidad maxima es obligatoria")
   @Column(nullable = false)    
    private int capacidad;

   @NotBlank(message = "El tipo de sala es obligatorio")
   @Column(nullable = false)    
    private String tipo;

   @NotNull(message = "El Id del cine es obligatorio")
   @Column(nullable = false)    
    private Long cineId;
    
}
