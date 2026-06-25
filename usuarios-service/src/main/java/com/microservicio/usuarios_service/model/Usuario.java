package com.microservicio.usuarios_service.model;

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

public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

   @NotNull(message = "El run es obligatorio")
   @Column(nullable = false)    
    private int run;

   @NotBlank(message = "El digito verificador es obligatorio")
   @Column(nullable = false)    
    private String dv;

   @NotBlank(message = "El nombre es obligatorio")
   @Column(nullable = false)      
    private String nombre;

   @NotBlank(message = "El apellido es obligatorio")
   @Column(nullable = false)      
    private String apellido;

   @NotBlank(message = "El correo es obligatorio")
   @Column(nullable = false)      
    private String correo;
    
}
