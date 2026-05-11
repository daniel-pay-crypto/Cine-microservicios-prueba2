package com.cine.ms_cliente.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClienteDetalleDTO {
    private Long id;
    private String nombreCompleto;
    private List<TicketDTO> tickets;
}