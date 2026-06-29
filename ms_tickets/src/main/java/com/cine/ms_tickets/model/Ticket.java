package com.cine.ms_tickets.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    
    //Atributos de la entidad Ticket
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank (message = "El asiento es obligatorio")
    @Size(min = 2, max = 100, message = "El asiento debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String puesto;

    @Min(value = 1000, message = "El precio mínimo del ticket es $1000")
    @Max(value = 50000, message = "El precio máximo no puede superar los $50000")
    @Column(nullable = false)
    private Integer precio;

    //Columnas para las relaciones con otras entidades (Cliente, Pelicula, Asiento)
    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Column(name = "pelicula_id", nullable = false)
    private Integer peliculaId;

    @Column(name = "asiento_id", nullable = false)
    private Integer asientoId;
}