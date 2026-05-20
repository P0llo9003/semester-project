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
(22008572,'4','Tomas','Rojas','tom.rojasm@duocuc.cl'),
(20648333,'4','Anais','Oporto','anai.oporto@duocuc.cl'),
(18765432,'1','Diego','Martinez','diego.martinez@gmail.com'),
(17654321,'2','Valentina','Perez','valentina.perez@gmail.com'),
(16543210,'3','Javier','Soto','javier.soto@gmail.com'),
(15432109,'5','Daniela','Muñoz','daniela.munoz@gmail.com'),
(14321098,'6','Felipe','Contreras','felipe.contreras@gmail.com'),
(13210987,'7','Antonia','Silva','antonia.silva@gmail.com'),
(12109876,'8','Matias','Torres','matias.torres@gmail.com');