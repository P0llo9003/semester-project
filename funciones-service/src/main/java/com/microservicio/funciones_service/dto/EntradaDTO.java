package com.microservicio.funciones_service.dto;

import com.microservicio.funciones_service.model.Entrada;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntradaDTO {

    private Long id;
    private Long funcionId;
    private Long usuarioId;
    private String asiento;

    public Entrada toModel() {
        return new Entrada(id,funcionId,usuarioId,asiento);
    }

    public static EntradaDTO fromModel(Entrada e) {
        if (e == null) return null;
        return new EntradaDTO(e.getId(),e.getFuncionId(),e.getUsuarioId(),e.getAsiento());
    }
}