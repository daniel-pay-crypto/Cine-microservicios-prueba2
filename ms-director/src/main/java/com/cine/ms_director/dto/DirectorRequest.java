package com.cine.ms_director.dto;

import lombok.Data;
import java.util.List;
@Data
public class DirectorRequest {
    private Long id;
    private String nombres;
    private String apellidos;
    private String nacionalidad;
    private List<PeliculaDTO> peliculas; // Aca se van aver las películas
}