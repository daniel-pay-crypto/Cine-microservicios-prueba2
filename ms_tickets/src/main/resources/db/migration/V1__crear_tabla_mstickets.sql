CREATE TABLE tickets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    pelicula_id INT NOT NULL,
    asiento_id INT NOT NULL,
    puesto VARCHAR(100) NOT NULL,
    precio INT NOT NULL
);

-- Datos de prueba iniciales para esta base de datos ms_tickets.
INSERT INTO tickets (cliente_id, pelicula_id, asiento, precio) VALUES (1, 101, 'A1', 4500.0);
INSERT INTO tickets (cliente_id, pelicula_id, asiento, precio) VALUES (1, 102, 'A2', 4500.0);
INSERT INTO tickets (cliente_id, pelicula_id, asiento, precio) VALUES (2, 101, 'C14', 5500.0);