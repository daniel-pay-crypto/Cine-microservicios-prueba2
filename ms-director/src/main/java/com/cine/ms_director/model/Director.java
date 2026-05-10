package com.cine.ms_director.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "directores")
@Data
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @Column(nullable = false)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @Column(nullable = false)
    @NotBlank(message = "La nacionalidad es obligatoria")
    private String nacionalidad;


}