package com.cine.ms_tipos.controller;

import com.cine.ms_tipos.dto.TipoRequest;
import com.cine.ms_tipos.dto.TipoResponse;
import com.cine.ms_tipos.service.TipoService;
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
@RequestMapping("/api/v1/tipos")
@RequiredArgsConstructor
public class TipoController {

    private final TipoService tipoService;

    @PostMapping
    public ResponseEntity<TipoResponse> crearTipo(@Valid @RequestBody TipoRequest request) {
        log.info("Peticion REST recibida para crear un nuevo tipo: {}", request.getNombre());
        TipoResponse response = tipoService.guardarTipo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }
    
    @GetMapping
    public ResponseEntity<List<TipoResponse>> listarTipos() {
        log.info("Peticion REST recibida para listar todos los tipos");
        List<TipoResponse> lista = tipoService.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoResponse> buscarTipo(@PathVariable Integer id) {
        log.info("Peticion REST recibida para buscar tipo por ID: {}", id);
        TipoResponse response = tipoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}