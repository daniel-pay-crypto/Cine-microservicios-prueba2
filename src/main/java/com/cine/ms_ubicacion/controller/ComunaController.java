package com.cine.ms_ubicacion.controller;

import com.cine.ms_ubicacion.dto.ComunaDTO;
import com.cine.ms_ubicacion.service.ComunaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comunas")
@RequiredArgsConstructor
public class ComunaController {

    private final ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarTodas() {
        return ResponseEntity.ok(comunaService.listarTodas());
    }

    @PostMapping("/region/{regionId}")
    public ResponseEntity<ComunaDTO> guardar(
            @Valid @RequestBody ComunaDTO comunaDTO,
            @PathVariable Long regionId) {
        
        ComunaDTO nuevaComuna = comunaService.guardar(comunaDTO, regionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaComuna);
    }
}