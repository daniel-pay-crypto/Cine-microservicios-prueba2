package com.cine.ms_genero.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "generos") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class GeneroModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(unique = true, length = 50, nullable = false) 
    private String nombre;

    @Column(nullable = true, length = 255)
    private String descripcion;
}