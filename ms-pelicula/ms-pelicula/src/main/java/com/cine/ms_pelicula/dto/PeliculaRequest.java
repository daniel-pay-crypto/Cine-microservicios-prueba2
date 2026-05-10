package com.cine.ms_pelicula.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PeliculaRequest {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La sinopsis es obligatoria")
    private String sinopsis;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser mayor a 0")
    private Integer duracionMinutos;

    @NotNull(message = "El ID del género es obligatorio")
    private Long generoId;

    @NotNull(message = "El ID del director es obligatorio")
    private Long directorId;
}
