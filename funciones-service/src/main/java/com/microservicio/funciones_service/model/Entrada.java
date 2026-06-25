package com.microservicio.funciones_service.model;

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
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @NotNull(message = "El Id de funcion es obligatorio")
   @Column(nullable = false)    
    private Long funcionId;

   @NotNull(message = "El Id de usuario es obligatorio")
   @Column(nullable = false)    
    private Long usuarioId;

   @NotBlank(message = "El asiento es obligatorio")
   @Column(nullable = false)    
    private String asiento;
}