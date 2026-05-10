package com.cine.ms_ubicacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunaDTO {
    
    @NotNull(message = "El Id de la comuna no puede ir vacia")
    private Long id;

    @NotBlank(message = "El nombre de la comuna no puede ir vacia")
    private String nombre;

    @NotBlank(message = "El nombre de la region no puede ir vacia")
    private String nombreRegion; //Este lo agrege para q sea mas facil de leer el el front

}
