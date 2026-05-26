--liquibase formatted sql

--changeset seba:1
CREATE TABLE funcion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pelicula_id BIGINT,
    sala_id BIGINT,
    fecha DATE,
    hora TIME,
    precio DOUBLE,
    estado VARCHAR(255)
);


--changeset seba:2
INSERT INTO funcion (pelicula_id, sala_id, fecha, hora, precio, estado) VALUES
(1, 1, '2026-05-26', '15:00:00', 4500, 'ACTIVA'),
(2, 2, '2026-05-26', '18:30:00', 6000, 'ACTIVA'),
(3, 3, '2026-05-27', '20:00:00', 7500, 'ACTIVA'),
(4, 4, '2026-05-27', '22:15:00', 8500, 'ACTIVA'),
(5, 5, '2026-05-28', '14:45:00', 5000, 'ACTIVA'),
(1, 6, '2026-05-28', '17:00:00', 6500, 'CANCELADA'),
(2, 7, '2026-05-29', '19:30:00', 7000, 'ACTIVA'),
(3, 8, '2026-05-29', '21:45:00', 9000, 'ACTIVA'),
(4, 9, '2026-05-30', '16:20:00', 5500, 'ACTIVA'),
(5, 10, '2026-05-30', '23:00:00', 10000, 'AGOTADA');


--changeset seba:3
CREATE TABLE entrada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    funcion_id BIGINT,
    usuario_id BIGINT,
    asiento VARCHAR(50)
);


--changeset seba:4
INSERT INTO entrada (funcion_id, usuario_id, asiento) VALUES
(1, 1, 'A1'),
(1, 2, 'A2'),
(2, 3, 'B1'),
(2, 4, 'B2'),
(3, 5, 'C1'),
(4, 1, 'D5'),
(5, 2, 'E3'),
(6, 3, 'F7'),
(7, 4, 'G2'),
(8, 5, 'H4');