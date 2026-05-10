package com.cine.ms_pelicula.dto;

import lombok.Data;


@Data
public class SalaDTO {
    private Long id;
    private String nombre;
    private Integer capacidad;
}