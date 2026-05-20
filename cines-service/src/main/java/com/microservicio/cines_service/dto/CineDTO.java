package com.microservicio.cines_service.dto;

import com.microservicio.cines_service.model.Cine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CineDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private int telefono;



    public Cine toModel() {
        return new Cine(id, nombre, direccion, ciudad, telefono);
    }

    public static CineDTO fromModel(Cine c) {
        if (c == null) return null;
        return new CineDTO(c.getId(),c.getNombre(),c.getDireccion(),c.getCiudad(),c.getTelefono());
    }
}