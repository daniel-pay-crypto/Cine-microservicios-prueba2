package com.cine.ms_tickets.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketRequest {

    @NotBlank(message = "El puesto/asiento es obligatorio")
    @Size(min = 2, max = 100, message = "El puesto debe tener entre 2 y 100 caracteres")
    private String puesto;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1000, message = "El precio mínimo del ticket es $1000")
    @Max(value = 50000, message = "El precio máximo no puede superar los $50000")
    private Integer precio;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer clienteId;

    @NotNull(message = "El ID de la película es obligatorio")
    private Integer peliculaId;

    @NotNull(message = "El ID del asiento es obligatorio")
    private Integer asientoId;

}