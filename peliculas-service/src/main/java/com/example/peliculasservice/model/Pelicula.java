package com.example.peliculasservice.model;

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
public class Pelicula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

   @NotBlank(message = "El titulo es obligatorio")
   @Column(nullable = false)    
    private String titulo;

   @NotBlank(message = "La descripcion es obligatoria")
   @Column(nullable = false)    
    private String descripcion;

   @NotNull(message = "La duracion es obligatoria")
   @Column(nullable = false)    
    private int duracion;

   @NotBlank(message = "El genero de la pelicula es obligatoria")
   @Column(nullable = false)    
    private String genero;

   @NotNull(message = "La clasificacion de la pelicula es obligatoria")
   @Column(nullable = false)    
    private String clasificacion;

}