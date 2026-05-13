-- liquibase formatted sql

-- changeset seba:1
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    run INT,
    dv VARCHAR(1),
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    correo VARCHAR (255)
);


-- changeset seba:2
INSERT INTO usuario(run, dv, nombre, apellido, correo) VALUES
(22109387,'9','Sebastian','Gomez','se.gomezl@duocuc.cl'),
(22008572,'4','Tomas','Rojas','tom.rojasm@duocuc.cl');