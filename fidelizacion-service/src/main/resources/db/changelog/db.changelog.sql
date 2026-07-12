-- liquibase formatted sql

-- changeset aju:1
CREATE TABLE fidelizacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    puntos INT,
    nivel VARCHAR(50)
);

-- changeset aju:2
INSERT INTO fidelizacion(usuario_id, puntos, nivel) VALUES
(1, 100, 'Bronce'),
(2, 250, 'Bronce'),
(3, 500, 'Plata'),
(4, 750, 'Plata'),
(5, 1000, 'Oro');