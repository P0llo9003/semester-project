-- liquibase formatted sql

-- changeset seba:1
CREATE TABLE cines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    direccion VARCHAR(255),
    ciudad VARCHAR(255),
    telefono INT 
);

-- changeset seba:2
INSERT INTO cines(nombre, direccion, ciudad, telefono) VALUES
('Cine Plaza Central', 'Av. Libertador 1234', 'Santiago', 911111111),
('Cine Star Mall', 'Calle Los Aromos 456', 'Valparaíso', 922222222),
('Cinema World', 'Av. Costanera 789', 'Concepción', 933333333),
('Movie Planet Norte', 'Pasaje Las Flores 321', 'Antofagasta', 944444444),
('CineMax Sur', 'Av. Los Leones 654', 'Temuco', 955555555),
('Pantalla Premium', 'Calle Principal 987', 'La Serena', 966666666),
('Cine Express', 'Av. España 147', 'Viña del Mar', 977777777),
('Cinema Boulevard', 'Boulevard Central 258', 'Rancagua', 988888888),
('MegaCine', 'Av. O Higgins 369', 'Puerto Montt', 999999999),
('Cine Galaxy', 'Calle San Martín 741', 'Talca', 910101010);