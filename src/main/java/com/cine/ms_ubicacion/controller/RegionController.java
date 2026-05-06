package com.cine.ms_ubicacion.controler;

import com.cine.ms_ubicacion.dto.RegionDTO;
import com.cine.ms_ubicacion.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/regiones")
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