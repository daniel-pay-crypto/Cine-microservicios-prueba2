package com.cine.ms_salas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaDTO {

    @NotNull(message = "El Id de la sala es obligatorio")
    private Long id;
    
    @NotBlank(message = "El nombre de la sala no puede estar vacio")
    private String nombre;

    @NotNull(message = "La capacidad de la sala es obligatorio")
    @Min(value = 10, message = "El valor minimo de capacidad es 10")
    private Integer capacidad;

    @NotNull(message = "El ID es obligatorio")
    private Long sucursalId;
}