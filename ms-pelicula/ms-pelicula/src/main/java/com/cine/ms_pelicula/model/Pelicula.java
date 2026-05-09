package com.cine.ms_pelicula.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "peliculas")
@Data
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String sinopsis;
    private Integer duracionMinutos;

    // Relaciones 1:N con ms-genero y ms-director.
    private Long generoId;
    private Long directorId;
}
