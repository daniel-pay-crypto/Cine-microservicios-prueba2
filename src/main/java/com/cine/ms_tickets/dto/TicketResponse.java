package com.cine.ms_tickets.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketResponse {

    private Integer id;
    private String puesto;
    private Integer precio;
    private Integer clienteId;
    private Integer peliculaId;
    private Integer asientoId;

}