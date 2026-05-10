package com.cine.ms_pelicula.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private Long id;
    private Integer numeroAsiento;
    private Integer precio;
}