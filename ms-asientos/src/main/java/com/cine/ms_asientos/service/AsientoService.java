package com.cine.ms_asientos.service;

import com.cine.ms_asientos.client.SalaClient;
import com.cine.ms_asientos.dto.AsientoDTO;
import com.cine.ms_asientos.model.Asiento;
import com.cine.ms_asientos.repository.AsientoRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsientoService {

    private final AsientoRepository asientoRepository;
    private final SalaClient salaClient;

    public List<AsientoDTO> listarTodos() {
        return asientoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public AsientoDTO guardar(AsientoDTO asientoDTO) {
        
        try {
            log.info("Verificando si la sala con ID {} existe en ms-salas...", asientoDTO.getSalaId());
            salaClient.obtenerPorSalaId(asientoDTO.getSalaId());
            log.info("¡Sala {} validada exitosamente!", asientoDTO.getSalaId());
        } catch (FeignException e) {
            log.error("Error: La sala con ID {} no existe en ms-salas.", asientoDTO.getSalaId());
            throw new RuntimeException("No se puede crear el asiento. La sala con ID " + asientoDTO.getSalaId() + " no fue encontrada.");
        }

        Asiento asiento = new Asiento();
        asiento.setFila(asientoDTO.getFila());
        asiento.setNumero(asientoDTO.getNumero());
        asiento.setEstado(asientoDTO.getEstado());
        asiento.setSalaId(asientoDTO.getSalaId());
        
        Asiento guardado = asientoRepository.save(asiento);
        log.info("Asiento guardado exitosamente: Fila {} Numero {} en la Sala ID {}", guardado.getFila(), guardado.getNumero(), guardado.getSalaId());
        
        return mapToDTO(guardado);
    }

    private AsientoDTO mapToDTO(Asiento asiento) {
        return new AsientoDTO(
            asiento.getId(),
            asiento.getFila(),
            asiento.getNumero(),
            asiento.getEstado(),
            asiento.getSalaId()
        );
    }
}