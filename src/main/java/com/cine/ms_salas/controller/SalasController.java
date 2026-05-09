package com.cine.ms_salas.controller;

import com.cine.ms_salas.dto.SalasDTO;
import com.cine.ms_salas.service.SalasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/funciones") //use el nombre de "funciones " para diferenciarlos
@RequiredArgsConstructor
public class SalasController {

    private final SalasService salasService;

    @PostMapping
    public ResponseEntity<SalasDTO> crearFuncion(@Valid @RequestBody SalasDTO salasDTO) {
        SalasDTO nuevaFuncion = salasService.crearFuncion(salasDTO);
        return new ResponseEntity<>(nuevaFuncion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SalasDTO>> listarFunciones() {
        return ResponseEntity.ok(salasService.listarTodas());
    }
}