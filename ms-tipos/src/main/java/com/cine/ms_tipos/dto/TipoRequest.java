package com.cine.ms_tipos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TipoRequest {

    @NotBlank(message = "El nombre del tipo es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String descripcion;

}