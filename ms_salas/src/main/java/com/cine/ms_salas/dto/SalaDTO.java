package com.cine.ms_salas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalaDTO {

    private Long id; 
    
    @NotBlank(message = "El nombre de la sala no puede estar vacio")
    private String nombre;

    @NotNull(message = "La capacidad de la sala es obligatorio")
    @Min(value = 10, message = "El valor minimo de capacidad es 10")
    private Integer capacidad;

    @NotNull(message = "El ID de sucursal es obligatorio")
    private Long sucursalId;

    @NotNull(message = "El ID del tipo de sala es obligatorio")
    private Integer tipoId;

    //Igualmente que el model tuve q hacer el constructor manual a ver si se solucionaban unos erroes
    public SalaDTO(Long id, String nombre, Integer capacidad, Long sucursalId, Integer tipoId) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.sucursalId = sucursalId;
        this.tipoId = tipoId;
    }
}