package com.cine.ms_asientos.controller;

import com.cine.ms_asientos.dto.AsientoDTO;
import com.cine.ms_asientos.service.AsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/asientos")
@RequiredArgsConstructor
public class AsientoController {

    private final AsientoService asientoService;

    @GetMapping
    public ResponseEntity<List<AsientoDTO>> listarTodos() {
        return ResponseEntity.ok(asientoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<AsientoDTO> guardar(@Valid @RequestBody AsientoDTO asientoDTO) {
        
        AsientoDTO nuevoAsiento = asientoService.guardar(asientoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAsiento);
    }
}