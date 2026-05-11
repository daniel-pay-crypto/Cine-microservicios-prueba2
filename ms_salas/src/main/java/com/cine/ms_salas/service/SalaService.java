package com.cine.ms_salas.service;

import com.cine.ms_salas.client.TipoClient;
import com.cine.ms_salas.dto.SalaDTO;
import com.cine.ms_salas.model.Sala;
import com.cine.ms_salas.repository.SalaRepository;
import feign.FeignException; 
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaService {

    private final SalaRepository salaRepository;
    private final TipoClient tipoClient; 

    public List<SalaDTO> listarTodas() {
        return salaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SalaDTO guardar(SalaDTO salaDTO) {
        // --- 1. VALIDAMOS QUE EL TIPO EXISTA EN MS-TIPOS ---
        try {
            log.info("Verificando si el tipo de sala ID {} existe...", salaDTO.getTipoId());
            tipoClient.obtenerPorId(salaDTO.getTipoId());
            log.info("Tipo validado correctamente.");
        } catch (FeignException e) {
            log.error("El tipo con ID {} no existe en ms-tipos.", salaDTO.getTipoId());
            throw new RuntimeException("No se puede crear la sala. El tipo ID " + salaDTO.getTipoId() + " no existe.");
        }

        // --- 2. GUARDAMOS LA SALA ---
        Sala sala = new Sala();
        sala.setNombre(salaDTO.getNombre());
        sala.setCapacidad(salaDTO.getCapacidad());
        sala.setSucursalId(salaDTO.getSucursalId()); 
        sala.setTipoId(salaDTO.getTipoId());

        Sala guardada = salaRepository.save(sala);
        log.info("Sala guardada: {} con capacidad para {} personas", guardada.getNombre(), guardada.getCapacidad());
        
        return mapToDTO(guardada);
    }

    private SalaDTO mapToDTO(Sala sala) {
        return new SalaDTO(
            sala.getId(),
            sala.getNombre(),
            sala.getCapacidad(),
            sala.getSucursalId(),
            sala.getTipoId() 
        );
    }
}