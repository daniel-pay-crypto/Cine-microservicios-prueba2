package com.cine.ms_salas_plural.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.cine.ms_salas_plural.dto.SalasDTO;
import com.cine.ms_salas_plural.model.Salas;
import com.cine.ms_salas_plural.repository.SalasRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalasService {

    @Autowired
    private SalasRepository salasRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public SalasDTO crearSalas(SalasDTO dto) {
        log.info("Intentando programar una nueva película en la sala física ID: {}", dto.getSalaId());

        try {
            webClientBuilder.build()
                    .get()
                    .uri("http://ms-salas/api/v1/sala/" + dto.getSalaId()) 
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
                    
            log.info("Sala física validada correctamente.");
            
        } catch (WebClientResponseException.NotFound e) {
            log.error("La sala física con ID {} no existe.", dto.getSalaId());
            throw new RuntimeException("No se puede programar. La sala física con ID " + dto.getSalaId() + " no existe.");
        } catch (Exception e) {
            log.error("Error de conexión con ms-salas físicas: {}", e.getMessage());
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

    public List<SalasDTO> obtenerPorSalaId(Long salaId) {
        List<Salas> salas = salasRepository.findBySalaId(salaId);
        if (salas.isEmpty()) {
            throw new RuntimeException("Programación para la sala física con ID " + salaId + " no encontrada");
        }
        return salas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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