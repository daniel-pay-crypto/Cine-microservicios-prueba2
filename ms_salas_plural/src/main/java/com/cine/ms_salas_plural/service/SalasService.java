package com.cine.ms_salas_plural.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.cine.ms_salas_plural.dto.SalasDTO;
import com.cine.ms_salas_plural.model.Salas;
import com.cine.ms_salas_plural.repository.SalasRepository;
import com.cine.ms_salas_plural.client.SalaClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalasService {

    private final SalasRepository salasRepository; 
    private final SalaClient salaClient;

    public SalasDTO crearSalas(SalasDTO dto) {
        log.info("Intentando programar una nueva película en la sala física ID: {}", dto.getSalaId());

        try {
            salaClient.buscarPorId(dto.getSalaId());
            log.info("Sala física validada correctamente.");
        } catch (FeignException.NotFound e) {
            log.error("La sala física con ID {} no existe.", dto.getSalaId());
            throw new RuntimeException("No se puede programar. La sala física con ID " + dto.getSalaId() + " no existe.");
        } catch (FeignException e) {
            log.error("Error de conexión con ms-salas: {}", e.getMessage());
            throw new RuntimeException("Error interno al validar la sala física.");
        }

        Salas nuevaSalas = new Salas();
        nuevaSalas.setSalaId(dto.getSalaId());
        nuevaSalas.setPeliculaId(dto.getPeliculaId());
        
        if (dto.getFechaInicio() == null) {
            nuevaSalas.setFechaInicio(LocalDateTime.now());
        } else {
            nuevaSalas.setFechaInicio(dto.getFechaInicio());
        }

        Salas guardada = salasRepository.save(nuevaSalas);
        log.info("Programación guardada con éxito con ID: {}", guardada.getId());

        return mapToDTO(guardada);
    }

    public List<SalasDTO> listarTodas() {
        log.info("Listando toda la programación de salas");
        return salasRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SalasDTO obtenerPorSalaId(Long salaId) {
        Salas sala = salasRepository.findBySalaId(salaId)
                .orElseThrow(() -> new RuntimeException("Programación para la sala con ID " + salaId + " no encontrada"));
        return mapToDTO(sala);
    }

    private SalasDTO mapToDTO(Salas entity) {
        SalasDTO dto = new SalasDTO();
        dto.setId(entity.getId());
        dto.setSalaId(entity.getSalaId()); 
        dto.setPeliculaId(entity.getPeliculaId());
        dto.setFechaInicio(entity.getFechaInicio());
        return dto;
    }
}