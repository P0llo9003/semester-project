package com.microservicio.confiteria_service.model;

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
public class Confiteria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

   @NotBlank(message = "Se debe asignar un nombre")
   @Column(nullable = false)	
	private String nombre;

   @NotBlank(message = "Se debe proporcionar una descripcion")
   @Column(nullable = false)	
	private String descripcion;

   @NotNull(message = "Se debe asignar un precio")
   @Column(nullable = false)	
    private int precio;

   @NotNull(message="El campo es obligatorio")
    private boolean disponibilidad;

}
