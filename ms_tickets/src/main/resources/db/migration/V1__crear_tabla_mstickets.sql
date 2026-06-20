CREATE TABLE tickets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    pelicula_id BIGINT NOT NULL,
    asiento VARCHAR(10) NOT NULL,
    precio DOUBLE NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'Activo',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Datos de prueba iniciales para esta base de datos ms_tickets.
INSERT INTO tickets (cliente_id, pelicula_id, asiento, precio) VALUES (1, 101, 'A1', 4500.0);
INSERT INTO tickets (cliente_id, pelicula_id, asiento, precio) VALUES (1, 102, 'A2', 4500.0);
INSERT INTO tickets (cliente_id, pelicula_id, asiento, precio) VALUES (2, 101, 'C14', 5500.0);