-- liquibase formatted sql

-- changeset aju:1
CREATE TABLE empleado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    run INT,
    dv VARCHAR(5),
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    correo VARCHAR(150),
    cargo VARCHAR(100),
    turno VARCHAR(50)
);

-- changeset aju:2
INSERT INTO empleado(run, dv, nombre, apellido, correo, cargo, turno) VALUES
(12345678, '9', 'Carlos', 'Soto', 'carlos.soto@cine.cl', 'Cajero', 'Mañana'),
(18765432, 'K', 'Ana', 'Rojas', 'ana.rojas@cine.cl', 'Supervisor', 'Tarde'),
(16543210, '5', 'Pedro', 'Diaz', 'pedro.diaz@cine.cl', 'Proyeccionista', 'Noche'),
(19876543, '2', 'Maria', 'Lopez', 'maria.lopez@cine.cl', 'Atencion Cliente', 'Mañana'),
(17654321, '7', 'Javiera', 'Torres', 'javiera.torres@cine.cl', 'Administrador', 'Tarde');