package com.cine.ms_genero.controller;

import com.cine.ms_genero.dto.GeneroRequest;
import com.cine.ms_genero.dto.GeneroResponse;
import com.cine.ms_genero.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    @PostMapping
    public ResponseEntity<GeneroResponse> crearGenero(@Valid @RequestBody GeneroRequest request) {
        log.info("Petición REST recibida para crear un nuevo género: {}", request.getNombre());
        GeneroResponse response = generoService.guardarGenero(request);
        
        // Devolvemos HTTP 201 Created
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET: /api/v1/generos -> Lista todos los géneros
    @GetMapping
    public ResponseEntity<List<GeneroResponse>> listarGeneros() {
        log.info("Petición REST recibida para listar todos los géneros");
        List<GeneroResponse> lista = generoService.listarTodos();
        
        if (lista.isEmpty()) {
            // Devolvemos 204 No Content si la lista está vacía
            return ResponseEntity.noContent().build();
        }
        // Devolvemos 200 OK con la lista
        return ResponseEntity.ok(lista);
    }

    // GET: /api/v1/generos/{id} -> Busca un género específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponse> buscarGenero(@PathVariable Integer id) {
        log.info("Petición REST recibida para buscar género por ID: {}", id);
        GeneroResponse response = generoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}