package com.cine.ms_cliente.exception;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
public class ErrorResponse {

    private String mensaje;
    private String detalle;
    private int status;
    private LocalDateTime timestamp;

}
