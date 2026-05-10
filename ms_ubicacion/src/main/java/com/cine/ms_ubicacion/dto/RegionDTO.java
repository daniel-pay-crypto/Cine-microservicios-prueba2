package com.cine.ms_ubicacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {

    @NotNull(message = "El ID de la region no puede estar vacia")
    private Long id;

    @NotBlank(message = "El nombre de la region no puede estar vacia")
    private String nombre;
}
