-- liquibase formatted sql

-- changeset aju:1
CREATE TABLE notificacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    titulo VARCHAR(255),
    mensaje VARCHAR(500),
    estado VARCHAR(50),
    fecha_envio DATETIME
);

-- changeset aju:2
INSERT INTO notificacion(usuario_id, titulo, mensaje, estado, fecha_envio) VALUES
(1, 'Compra Exitosa', 'Tu compra se realizo correctamente', 'ENVIADA', NOW()),
(2, 'Reserva Confirmada', 'Tu reserva ha sido confirmada', 'ENVIADA', NOW()),
(3, 'Promocion Disponible', 'Tienes una nueva promocion disponible', 'PENDIENTE', NOW()),
(4, 'Funcion Actualizada', 'La funcion seleccionada ha cambiado de horario', 'ENVIADA', NOW()),
(5, 'Bienvenido', 'Gracias por registrarte en CineApp', 'LEIDA', NOW());