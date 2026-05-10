package com.cine.ms_genero.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneroResponse {

    private Integer id;
    private String nombre;
    private String descripcion;

}