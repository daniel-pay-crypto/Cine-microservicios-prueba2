package com.cine.ms_salas_plural.service;

import com.cine.ms_salas.model.Sala;
import com.cine.ms_salas.repository.SalaRepository;
import com.cine.ms_salas_plural.dto.SalasDTO;
import com.cine.ms_salas_plural.model.Salas;
import com.cine.ms_salas_plural.repository.SalasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalasService {

    private final SalasRepository salasRepository;
    private final SalaRepository salaRepository; //Este es para validar que la sala exista

    public SalasDTO crearFuncion(SalasDTO dto) {
        log.info("Intentando programar una nueva película en la sala ID: {}", dto.getSalaId());

        // Validamos que la sala física exista en la base de datos
        Sala salaFisica = salaRepository.findById(dto.getSalaId())
                .orElseThrow(() -> {
                    log.error("La sala física ID {} no existe. Abortando programación.", dto.getSalaId());
                    return new RuntimeException("No se puede programar: La sala física no existe");
                });

        Salas nuevaFuncion = new Salas();
        nuevaFuncion.setSala(salaFisica);
        nuevaFuncion.setPeliculaId(dto.getPeliculaId());
        
        //Le agregamos fecha actual si el usuario no envía una
        if (dto.getFechaInicio() == null) {
            nuevaFuncion.setFechaInicio(LocalDateTime.now());
        } else {
            nuevaFuncion.setFechaInicio(dto.getFechaInicio());
        }

        Salas guardada = salasRepository.save(nuevaFuncion);
        log.info("Programación guardada con éxito con ID: {}", guardada.getId());

        return mapToDTO(guardada);
    }

    public List<SalasDTO> listarTodas() {
        log.info("Listando toda la programación de salas");
        return salasRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Esto es para convertir de Entidad a DTO
    private SalasDTO mapToDTO(Salas entity) {
        SalasDTO dto = new SalasDTO();
        dto.setId(entity.getId());
        dto.setSalaId(entity.getSala().getId());
        dto.setPeliculaId(entity.getPeliculaId());
        dto.setFechaInicio(entity.getFechaInicio());
        return dto;
    }
}