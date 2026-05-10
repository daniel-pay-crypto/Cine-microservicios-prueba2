package com.cine.ms_ubicacion.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.cine.ms_ubicacion.dto.RegionDTO;
import com.cine.ms_ubicacion.model.Region;
import com.cine.ms_ubicacion.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {

    private final RegionRepository regionRepository;

    public List<RegionDTO> listarTodas() {
        return regionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public RegionDTO guardar(RegionDTO regionDTO) { 
        Region region = new Region();
        region.setNombre(regionDTO.getNombre());

        Region guardada = regionRepository.save(region);
        log.info("Region guardada: {}", guardada.getNombre()); 
        
        return mapToDTO(guardada);
    }

    private RegionDTO mapToDTO(Region region) {
        return new RegionDTO(
            region.getId(),
            region.getNombre()
        );
    }
}