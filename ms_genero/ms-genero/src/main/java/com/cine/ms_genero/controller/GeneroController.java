package com.cine.ms_genero.controller;

import com.cine.ms_genero.dto.GeneroRequest;
import com.cine.ms_genero.dto.GeneroResponse;
import com.cine.ms_genero.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    @PostMapping
    public ResponseEntity<GeneroResponse> crearGenero(@Valid @RequestBody GeneroRequest request) {
        log.info("Peticion REST recibida para crear un nuevo genero: {}", request.getNombre());
        GeneroResponse response = generoService.guardarGenero(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GeneroResponse>> listarGeneros() {
        log.info("Peticion REST recibida para listar todos los generos");
        List<GeneroResponse> lista = generoService.listarTodos();
        
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponse> buscarGenero(@PathVariable Integer id) {
        log.info("Peticion REST recibida para buscar genero por ID: {}", id);
        GeneroResponse response = generoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}