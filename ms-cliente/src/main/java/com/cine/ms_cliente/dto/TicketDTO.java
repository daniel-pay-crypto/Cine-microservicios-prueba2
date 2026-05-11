package com.cine.ms_cliente.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private Long id;
    private String asiento;
    private Double precio;
}