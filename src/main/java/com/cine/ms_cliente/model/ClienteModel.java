package com.cine.ms_cliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

public class ClienteModel {



@Entity // Marca la clase como una entidad JPA que será una tabla en la base de datos.
@Table(name = "clientes") // Especifica que la tabla se llamará "clientes".
@Data // Magia de Lombok: Genera automáticamente Getters, Setters, toString y equals.
@NoArgsConstructor // Magia de Lombok: Crea un constructor vacío obligatorio para JPA[cite: 2, 3].
@AllArgsConstructor // Magia de Lombok: Crea un constructor con todos los atributos[cite: 3].
public class Cliente {

    @Id // Indica que este campo es la Llave Primaria (PK)[cite: 2].
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea auto-incremental en MySQL[cite: 2].
    private Integer id;

    
    @Column(unique = true, length = 13, nullable = false) // Configura la columna: única, máximo 13 caracteres y no nula[cite: 2].
    private String run;

    @Column(nullable = false) // Indica que la columna no puede contener valores nulos[cite: 2].
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(unique = true, nullable = false) // El correo debe ser único para cada cliente.
    private String correo;

    @Column(name = "fecha_nacimiento", nullable = true) // Puede tener otro nombre en la BD ("fecha_nacimiento")[cite: 2].
    private LocalDate fechaNacimiento;
    

  }
}