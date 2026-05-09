package com.cine.ms_salas.service;

import com.cine.ms_salas.dto.SalaDTO;
import com.cine.ms_salas.model.Sala;
import com.cine.ms_salas.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaService {

    private final SalaRepository salaRepository;

    public List<SalaDTO> listarTodas() {
        return salaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SalaDTO guardar(SalaDTO salaDTO) {
        Sala sala = new Sala();
        sala.setNombre(salaDTO.getNombre());
        sala.setCapacidad(salaDTO.getCapacidad());
        
        // Guardamos el ID de la sucursal, pero aun no se crea la relacon
        sala.setSucursalId(salaDTO.getSucursalId()); 

        Sala guardada = salaRepository.save(sala);
        log.info("Sala guardada: {} con capacidad para {} personas", guardada.getNombre(), guardada.getCapacidad());
        
        return mapToDTO(guardada);
    }

    private SalaDTO mapToDTO(Sala sala) {
        return new SalaDTO(
            sala.getId(),
            sala.getNombre(),
            sala.getCapacidad(),
            sala.getSucursalId()
        );
    }
}