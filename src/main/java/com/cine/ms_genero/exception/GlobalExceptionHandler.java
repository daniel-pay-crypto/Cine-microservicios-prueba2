package com.cine.ms_genero.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j // Usamos logs estructurados para que quede el registro del error en consola
@RestControllerAdvice // Convierte esta clase en el manejador global de excepciones
public class GlobalExceptionHandler {

    // 1. Atrapa los errores de validación de los DTOs (@NotBlank, @Size, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        log.warn("Error de validación atrapado en @ControllerAdvice");

        // Extrae todos los errores de los campos (ej: "nombre: El nombre del género es obligatorio")
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse error = ErrorResponse.builder()
                .mensaje("Errores de validación en los datos enviados")
                .detalle(detalle)
                .status(HttpStatus.BAD_REQUEST.value()) // Código HTTP 400
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(error); 
    }

    // 2. Atrapa los errores de negocio que lanzamos manualmente (Ej: "Ya existe el género")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        log.warn("Error de lógica de negocio atrapado: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .mensaje("Error en la operación")
                .detalle(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value()) // Código HTTP 400
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    // 3. Atrapa cualquier otro error inesperado (Ej: se cayó la base de datos)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Error interno inesperado del servidor: ", ex);

        ErrorResponse error = ErrorResponse.builder()
                .mensaje("Error Interno en el servidor")
                .detalle(ex.getMessage()) // Nota: en producción real suele ocultarse el getMessage() por seguridad
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) // Código HTTP 500
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}