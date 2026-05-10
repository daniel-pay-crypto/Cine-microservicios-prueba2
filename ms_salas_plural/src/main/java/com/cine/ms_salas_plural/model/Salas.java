package com.cine.ms_salas_plural.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Table(name = "salas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sala_id")
    private Long salaId;
    
    //Lo coloco asi pormientras aun no se como lo tienen
    @Column(name = "pelicula_id")
    private Long peliculaId;

    //Lo mismo q el anterior
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
}