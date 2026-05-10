package com.cine.ms_salas_plural.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.cine.ms_salas_plural.dto.SalasDTO;
import com.cine.ms_salas_plural.model.Salas;
import com.cine.ms_salas_plural.repository.SalasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalasService {

    //Usamos el repositorio propio de este microservicio
    private final SalasRepository salasRepository; 

    public SalasDTO crearSalas(SalasDTO dto) {
        log.info("Intentando programar una nueva película en la sala ID: {}", dto.getSalaId());

        /* 
        Aquí ya no validamos con salaRepository.findById porque la tabla salas 
        está en otro microservicio. Luego usaremos Feign para validar.
        */

        Salas nuevaSalas = new Salas();
        
        //Ahora asignamos el ID directamente como un Long
        nuevaSalas.setSalaId(dto.getSalaId());
        nuevaSalas.setPeliculaId(dto.getPeliculaId());
        
        // Le agregamos fecha actual si el usuario no envía una
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

    // Esto es para convertir de Entidad a DTO
    private SalasDTO mapToDTO(Salas entity) {
        SalasDTO dto = new SalasDTO();
        dto.setId(entity.getId());
        
        // Obtenemos el ID directamente del campo salaId de la entidad
        dto.setSalaId(entity.getSalaId()); 
        dto.setPeliculaId(entity.getPeliculaId());
        dto.setFechaInicio(entity.getFechaInicio());
        return dto;
    }
}