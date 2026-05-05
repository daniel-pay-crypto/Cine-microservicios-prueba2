package com.cine.ms_cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequest {

    @NotBlank(message = "El RUN no puede estar vacío")
    @Size(max = 13, message = "El RUN no puede tener más de 13 caracteres")
    private String run;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un formato de correo válido")
    private String correo;

    private LocalDate fechaNacimiento;

}
