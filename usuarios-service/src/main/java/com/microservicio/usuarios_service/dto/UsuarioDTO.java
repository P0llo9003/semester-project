package com.microservicio.usuarios_service.dto;


import com.microservicio.usuarios_service.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private int run;
    private String dv;
    private String nombre;
    private String apellido;
    private String correo;

    public Usuario toModel() {
        return new Usuario(id, run, dv, nombre, apellido, correo);
    }

    public static UsuarioDTO fromModel(Usuario u) {
        if (u == null) return null;
        return new UsuarioDTO(u.getId(), u.getRun(), u.getDv(), u.getNombre(), u.getApellido(), u.getCorreo());
    }
}
