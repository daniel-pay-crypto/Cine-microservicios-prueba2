package com.cine.ms_sucursales.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {

    @NotNull(message = "El Id no puede estar vacio")
    private Long id;

    @NotBlank(message = "El nombre de la sucursal no debe estar vacio")
    private String nombre;

    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccion;

    @NotNull(message = "El Id de la comuna no puede ir vacia")
    private Long comunaid;
}