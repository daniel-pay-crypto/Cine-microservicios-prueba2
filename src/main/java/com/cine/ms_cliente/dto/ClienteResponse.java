package com.cine.ms_cliente.dto;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDate;
@Data
@Builder
public class ClienteResponse {

    private Integer id;
    private String run;
    private String nombres;
    private String apellidos;
    private String correo;
    private LocalDate fechaNacimiento;
}
