package com.microservicio.fidelizacion_service.dto;

import com.microservicio.fidelizacion_service.model.Fidelizacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FidelizacionDTO {

    private Long id;
    private Long usuarioId;
    private int puntos;
    private String nivel;

    public Fidelizacion toModel() {
        return new Fidelizacion(id, usuarioId, puntos, nivel);
    }

    public static FidelizacionDTO fromModel(Fidelizacion f) {
        if (f == null) return null;
        return new FidelizacionDTO(f.getId(), f.getUsuarioId(), f.getPuntos(), f.getNivel());
    }
}