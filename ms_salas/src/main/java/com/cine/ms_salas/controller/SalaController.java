package com.cine.ms_salas.controller;

import com.cine.ms_salas.dto.SalaDTO;
import com.cine.ms_salas.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sala")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listarSalas() {
        return ResponseEntity.ok(salaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<SalaDTO> crearSala(@RequestBody SalaDTO salaDTO) {
        SalaDTO nuevaSala = salaService.guardar(salaDTO);
        return new ResponseEntity<>(nuevaSala, HttpStatus.CREATED);
    }
}