package com.cine.ms_director.dto;

import lombok.Data;
import java.util.List;

@Data
public class DirectorResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String nacionalidad;
    private List<PeliculaDTO> peliculas;
}