package com.cine.ms_ubicacion.controller;

import com.cine.ms_ubicacion.dto.RegionDTO;
import com.cine.ms_ubicacion.service.RegionService;
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
@RequestMapping("/api/v1/regiones")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarTodas() {
        return ResponseEntity.ok(regionService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<RegionDTO> guardar(@Valid @RequestBody RegionDTO regionDTO) {
        RegionDTO nuevaRegion = regionService.guardar(regionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRegion);
    }
}