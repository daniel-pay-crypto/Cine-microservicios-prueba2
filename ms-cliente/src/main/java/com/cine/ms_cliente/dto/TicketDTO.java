package com.cine.ms_cliente.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private Long id;
    private String asiento;
    private Double precio;
    private Long ClienteId; // ID de la función a la que pertenece el ticket

}
