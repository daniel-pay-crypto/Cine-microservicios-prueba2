package com.cine.ms_tipos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoResponse {

    private Integer id;
    private String nombre;
    private String descripcion;

}