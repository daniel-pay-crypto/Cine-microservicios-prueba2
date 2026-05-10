package com.cine.ms_pelicula.dto;

import lombok.Data;


@Data
public class TicketDTO {
    private Long id;
    private Double precio;
    private String asiento;
}