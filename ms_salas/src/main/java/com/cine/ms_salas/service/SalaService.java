package com.cine.ms_salas.service;

import com.cine.ms_salas.client.SucursalClient;
import com.cine.ms_salas.client.TipoClient;
import com.cine.ms_salas.dto.SalaDTO;
import com.cine.ms_salas.model.Sala;
import com.cine.ms_salas.repository.SalaRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SalaService {

    @Autowired
    private  SalaRepository salaRepository;

    @Autowired
    private TipoClient tipoClient;

    @Autowired
    private SucursalClient sucursalClient;

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
        } catch (FeignException.NotFound e) {
            log.error("El tipo con ID {} no existe en ms-tipos.", salaDTO.getTipoId());
            throw new RuntimeException("No se puede crear la sala. El tipo ID " + salaDTO.getTipoId() + " no existe.");
        } catch (FeignException e) {
            log.error("Error de conexión con ms-tipos: {}", e.getMessage());
            throw new RuntimeException("Error interno al validar el tipo de sala.");
        }

        // --- 2. VALIDAMOS QUE LA SUCURSAL EXISTA EN MS-SUCURSALES ---
        try {
            log.info("Verificando si la sucursal ID {} existe...", salaDTO.getSucursalId());
            sucursalClient.obtenerPorId(salaDTO.getSucursalId());
            log.info("Sucursal validada correctamente.");
        } catch (FeignException.NotFound e) {
            log.error("La sucursal con ID {} no existe en ms-sucursales.", salaDTO.getSucursalId());
            throw new RuntimeException("No se puede crear la sala. La sucursal ID " + salaDTO.getSucursalId() + " no existe.");
        } catch (FeignException e) {
            log.error("Error de conexión con ms-sucursales: {}", e.getMessage());
            throw new RuntimeException("Error interno al validar la sucursal.");
        }

        // --- 3. GUARDAMOS LA SALA ---
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