package com.cine.ms_ubicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunaDTO {

    private Long id;
    private String nombre;
    private String nombreRegion; //Este lo agrege para q sea mas facil de leer el el front

}
