CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO clientes (nombre, apellido, email) VALUES ('Juan', 'Pérez', 'juan.perez@example.com');
INSERT INTO clientes (nombre, apellido, email) VALUES ('María', 'Gómez', 'maria.gomez@example.com');
