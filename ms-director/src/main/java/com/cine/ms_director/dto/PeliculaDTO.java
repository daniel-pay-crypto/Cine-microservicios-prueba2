package com.cine.ms_director.dto;
import lombok.Data;

@Data
public class PeliculaDTO {
    private Long id;
    private String titulo;
    private Integer duracionMinutos;
}