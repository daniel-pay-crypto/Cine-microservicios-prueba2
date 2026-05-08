package com.cine.ms_sucursales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la sucursal es obligatorio, no puede estar en blanco")
    private String nombre;

    @NotBlank(message = "Debes ingresar una direccion valida")
    private String direccion;

    @NotNull(message = "El ID de la comuna es obligatorio")
    @Column(name = "comuna_id")
    private Long comunaId;
}