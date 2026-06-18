package com.cine.ms_salas_plural.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.ms_salas_plural.dto.SalasDTO;
import com.cine.ms_salas_plural.service.SalasService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/salas")

public class SalasController {

    @Autowired
    private SalasService salasService;

    @PostMapping
    public ResponseEntity<SalasDTO> crearFuncion(@Valid @RequestBody SalasDTO salasDTO) {
        SalasDTO nuevaSalas = salasService.crearSalas(salasDTO);
        return new ResponseEntity<>(nuevaSalas, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SalasDTO>> listarSalas() {
        return ResponseEntity.ok(salasService.listarTodas());
    }

    @GetMapping("/{salaId}")
    public ResponseEntity<List<SalasDTO>> obtenerPorSalaId(@PathVariable("salaId") Long salaId) {
        List<SalasDTO> salas = salasService.obtenerPorSalaId(salaId);
        return ResponseEntity.ok(salas); 
    }

}