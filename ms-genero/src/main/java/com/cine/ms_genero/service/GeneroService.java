package com.cine.ms_genero.service;

import com.cine.ms_genero.dto.GeneroRequest;
import com.cine.ms_genero.dto.GeneroResponse;
import com.cine.ms_genero.model.GeneroModel;
import com.cine.ms_genero.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroResponse guardarGenero(GeneroRequest request) {
        log.info("Iniciando guardado de nuevo género con nombre: {}", request.getNombre());

        if (generoRepository.existsByNombre(request.getNombre())) {
            log.error("Error al guardar: Ya existe el género {}", request.getNombre());
            throw new RuntimeException("Ya existe un género con el nombre: " + request.getNombre());
        }

        GeneroModel genero = new GeneroModel();
        genero.setNombre(request.getNombre());
        genero.setDescripcion(request.getDescripcion());

        GeneroModel generoGuardado = generoRepository.save(genero);
        log.info("Género guardado exitosamente con ID: {}", generoGuardado.getId());

        return mapToResponse(generoGuardado);
    }

    public List<GeneroResponse> listarTodos() {
        log.info("Buscando todos los géneros registrados");
        return generoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public GeneroResponse buscarPorId(Integer id) {
        log.info("Buscando género con ID: {}", id);
        GeneroModel genero = generoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Género no encontrado con ID: {}", id);
                    return new RuntimeException("Género no encontrado con ID: " + id);
                });
        return mapToResponse(genero);
    }

    private GeneroResponse mapToResponse(GeneroModel genero) {
        return GeneroResponse.builder()
                .id(genero.getId())
                .nombre(genero.getNombre())
                .descripcion(genero.getDescripcion())
                .build();
    }
}