package com.cine.ms_salas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salas")
@Data
@NoArgsConstructor
public class Sala {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private Integer capacidad;
    
    @Column(name = "sucursal_id")
    private Long sucursalId;

    @Column(name = "tipo_id", nullable = false)
    private Integer tipoId;
    
    //Aca tuve que hacer un constructor manual pq no me funcionaba con el AllArgsConstructor
    public Sala(Long id, String nombre, Integer capacidad, Long sucursalId, Integer tipoId) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.sucursalId = sucursalId;
        this.tipoId = tipoId;
    }
}