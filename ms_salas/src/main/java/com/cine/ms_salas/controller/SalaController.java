package com.cine.ms_salas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cine.ms_salas.dto.SalaDTO;
import com.cine.ms_salas.service.SalaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sala")
public class SalaController {
    
    @Autowired
    private SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listarSalas() {
        List<SalaDTO> lista = salaService.listarTodas();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SalaDTO> crearSala(@Valid @RequestBody SalaDTO salaDTO) {
        SalaDTO nuevaSala = salaService.guardar(salaDTO); 
        return new ResponseEntity<>(nuevaSala, HttpStatus.CREATED);
    }
}