package com.cine.ms_salas.service;

import com.cine.ms_salas.dto.SalaDTO;
import com.cine.ms_salas.model.Sala;
import com.cine.ms_salas.repository.SalaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<SalaDTO> listarTodas() {
        return salaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SalaDTO guardar(SalaDTO salaDTO) {
        
        try {
            log.info("Verificando si el tipo de sala ID {} existe...", salaDTO.getTipoId());
            webClientBuilder.build()
                    .get()
                    .uri("http://ms-tipos/api/v1/tipo/" + salaDTO.getTipoId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            log.info("Tipo validado correctamente.");
        } catch (WebClientResponseException.NotFound e) {
            log.error("El tipo con ID {} no existe en ms-tipos.", salaDTO.getTipoId());
            throw new RuntimeException("No se puede crear la sala. El tipo ID " + salaDTO.getTipoId() + " no existe.");
        } catch (Exception e) {
            log.error("Error de conexión con ms-tipos: {}", e.getMessage());
            throw new RuntimeException("Error interno al validar el tipo de sala.");
        }

        try {
            log.info("Verificando si la sucursal ID {} existe...", salaDTO.getSucursalId());
            webClientBuilder.build()
                    .get()
                    .uri("http://ms-sucursales/api/v2/sucursales/" + salaDTO.getSucursalId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            log.info("Sucursal validada correctamente.");
        } catch (WebClientResponseException.NotFound e) {
            log.error("La sucursal con ID {} no existe en ms-sucursales.", salaDTO.getSucursalId());
            throw new RuntimeException("No se puede crear la sala. La sucursal ID " + salaDTO.getSucursalId() + " no existe.");
        } catch (Exception e) {
            log.error("Error de conexión con ms-sucursales: {}", e.getMessage());
            throw new RuntimeException("Error interno al validar la sucursal.");
        }

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