package com.microservicio.funciones_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.microservicio.funciones_service.model.Funcion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionDTO {
    private Long id;
    private Long peliculaId;
    private Long salaId;
    private LocalDate fecha;
    private LocalTime hora;
    private Double precio;
    private String estado;

    public Funcion toModel() {
        return new Funcion(id,peliculaId,salaId,fecha,hora,precio,estado);
    }

    public static FuncionDTO fromModel(Funcion f) {
        if (f == null) return null;
        return new FuncionDTO(f.getId(),f.getPeliculaId(),f.getSalaId(),f.getFecha(),f.getHora(),f.getPrecio(),f.getEstado());
    }
}
