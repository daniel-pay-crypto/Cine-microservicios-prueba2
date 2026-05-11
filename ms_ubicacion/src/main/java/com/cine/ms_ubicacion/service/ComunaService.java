package com.cine.ms_ubicacion.service;

import com.cine.ms_ubicacion.dto.ComunaDTO;
import com.cine.ms_ubicacion.model.Comuna;
import com.cine.ms_ubicacion.model.Region;
import com.cine.ms_ubicacion.repository.ComunaRepository;
import com.cine.ms_ubicacion.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComunaService {

    private final ComunaRepository comunaRepository;
    private final RegionRepository regionRepository;

    public List<ComunaDTO> listarTodas() {
        return comunaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ComunaDTO guardar(ComunaDTO comunaDTO, Long regionId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Error: La Región con ID " + regionId + " no existe."));

        Comuna comuna = new Comuna();
        comuna.setNombre(comunaDTO.getNombre());
        comuna.setRegion(region);
        Comuna guardada = comunaRepository.save(comuna);
        log.info("Comuna guardada:  en la región ", guardada.getNombre(), region.getNombre());
        
        return mapToDTO(guardada);
    }

    private ComunaDTO mapToDTO(Comuna comuna) {
        return new ComunaDTO(
            comuna.getId(),
            comuna.getNombre(),
            comuna.getRegion().getNombre()
        );
    }

    public ComunaDTO buscarPorId(Long id) {
        log.info("Buscando comuna con ID: {}", id);
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La comuna con ID " + id + " no existe."));
        return mapToDTO(comuna);
    }
    
}