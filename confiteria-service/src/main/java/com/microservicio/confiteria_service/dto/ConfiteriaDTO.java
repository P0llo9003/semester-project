package com.microservicio.confiteria_service.dto;

import com.microservicio.confiteria_service.model.Confiteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiteriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int precio;
    private boolean disponibilidad;



    public Confiteria toModel() {
        return new Confiteria(id, nombre, descripcion, precio, disponibilidad);
    }

    public static ConfiteriaDTO fromModel(Confiteria c) {
        if (c == null) return null;
        return new ConfiteriaDTO(c.getId(),c.getNombre(),c.getDescripcion(),c.getPrecio(),c.isDisponibilidad());
    }
}