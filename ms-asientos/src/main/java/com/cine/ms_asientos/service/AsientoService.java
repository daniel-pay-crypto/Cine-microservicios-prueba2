package com.cine.ms_asientos.service;

import com.cine.ms_asientos.dto.AsientoDTO;
import com.cine.ms_asientos.model.Asiento;
import com.cine.ms_asientos.repository.AsientoRepository;
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

    public List<AsientoDTO> listarTodos() {
        return asientoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public AsientoDTO guardar(AsientoDTO asientoDTO) {
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