package com.cine.ms_asientos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoDTO {

    private Long id; 

    @NotBlank(message = "La fila del asiento no puede ir vacia")
    private String fila;

    @NotNull(message = "El numero del asiento no puede ir vacio")
    private Integer numero;

    @NotBlank(message = "El estado del asiento no puede ir vacio")
    private String estado;

    @NotNull(message = "El Id de la sala no puede ir vacio")
    private Long salaId;

}