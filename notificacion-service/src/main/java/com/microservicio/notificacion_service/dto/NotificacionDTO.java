package com.microservicio.notificacion_service.dto;

import java.time.LocalDateTime;

import com.microservicio.notificacion_service.model.Notificacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionDTO {

    private Long id;
    private Long usuarioId;
    private String titulo;
    private String mensaje;
    private String estado;
    private LocalDateTime fechaEnvio;

    public Notificacion toModel() {
        return new Notificacion(id, usuarioId, titulo, mensaje, estado, fechaEnvio);
    }

    public static NotificacionDTO fromModel(Notificacion n) {
        if (n == null) return null;
        return new NotificacionDTO(n.getId(), n.getUsuarioId(), n.getTitulo(), n.getMensaje(), n.getEstado(), n.getFechaEnvio());
    }
}