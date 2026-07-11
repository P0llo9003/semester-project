package com.microservicio.empleados_service.dto;

import com.microservicio.empleados_service.model.Empleado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoDTO {

    private Long id;
    private int run;
    private String dv;
    private String nombre;
    private String apellido;
    private String correo;
    private String cargo;
    private String turno;

    public Empleado toModel() {
        return new Empleado(
                id,
                run,
                dv,
                nombre,
                apellido,
                correo,
                cargo,
                turno);
    }

    public static EmpleadoDTO fromModel(Empleado empleado) {

        if (empleado == null) return null;

        return new EmpleadoDTO(empleado.getId(),empleado.getRun(),empleado.getDv(),empleado.getNombre(), empleado.getApellido(),empleado.getCorreo(),empleado.getCargo(), empleado.getTurno());
    }
}