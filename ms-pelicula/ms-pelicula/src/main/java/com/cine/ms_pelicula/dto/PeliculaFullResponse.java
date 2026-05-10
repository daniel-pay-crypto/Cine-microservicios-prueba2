package com.cine.ms_pelicula.dto;

import lombok.Data;
import java.util.List;

@Data
public class PeliculaFullResponse {

    private Long id;
    private String titulo;
    private List<GeneroDTO> generos; // Relación 1:N con ms-genero
    private List<TicketDTO> tickets; // Relación 1:N con ms-tickets
    private List<SalaDTO> salas;     // Relación 1:N con ms-salas
}
