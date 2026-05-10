package com.cine.ms_salas_plural.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SalasDTO {
    private Long id;

    @NotNull(message = "El ID de la sala física es obligatorio")
    private Long salaId;

    @NotNull(message = "El ID de la película es obligatorio")
    private Long peliculaId;

    private LocalDateTime fechaInicio;
}