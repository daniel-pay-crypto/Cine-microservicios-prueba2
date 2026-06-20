package com.cine.ms_tickets.controller;

import com.cine.ms_tickets.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import org.springframework.web.bind.MethodArgumentNotValidException;//Para manejar errores de validación de argumentos en los controladores

@ControllerAdvice
public class GlobalExceptionHandler {

    // Variable para registrar mensajes de log
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
       
        log.error("Error de validación con los datos ingresados");
        

        String mensajeError = ex.getFieldError().getDefaultMessage();
                
        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Errores de validación con los datos enviados...");
        error.setDetalle(mensajeError);
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        log.error("Error interno del servidor: {}, Disculpe las molestias.", ex.getMessage());
        
        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Error Interno en el servidor");
        error.setDetalle(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
