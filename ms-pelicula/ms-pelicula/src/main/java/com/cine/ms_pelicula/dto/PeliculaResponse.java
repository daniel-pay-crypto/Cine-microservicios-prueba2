package com.cine.ms_pelicula.dto;

import lombok.Data;

@Data
public class PeliculaResponse {
    private Long id;
    private String titulo;
    private String sinopsis;
    private Integer duracionMinutos;
    private Long generoId;
    private Long directorId;
}