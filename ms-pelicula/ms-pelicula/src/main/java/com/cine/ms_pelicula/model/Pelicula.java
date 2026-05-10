package com.cine.ms_pelicula.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "peliculas")
@Data // Si sigue en rojo, mira el paso de abajo
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título de la película no puede estar vacío")
    private String titulo;;
    
    @NotNull(message = "La duración de la película no puede ser nula")
    private Integer duracionMinutos;

    @Column(name = "genero_id")
    @NotNull(message = "El ID del género no puede ser nulo")
    private Long generoId;

    @Column(name = "director_id")
    @NotNull(message = "El ID del director no puede ser nulo")
    private Long directorId; 
}