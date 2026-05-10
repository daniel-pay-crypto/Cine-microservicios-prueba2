package com.cine.ms_tipos.service;

import com.cine.ms_tipos.dto.TipoRequest;
import com.cine.ms_tipos.dto.TipoResponse;
import com.cine.ms_tipos.model.TipoModel;
import com.cine.ms_tipos.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipoService {

    private final TipoRepository tipoRepository;

    public TipoResponse guardarTipo(TipoRequest request) {
        log.info("Iniciando proceso para guardar nuevo tipo: {}", request.getNombre());

        if (tipoRepository.existsByNombre(request.getNombre())) {
            log.warn("El tipo '{}' ya existe en la base de datos", request.getNombre());
            throw new RuntimeException("Ya existe un tipo registrado con el nombre: " + request.getNombre());
        }

        TipoModel tipo = new TipoModel();
        tipo.setNombre(request.getNombre());
        tipo.setDescripcion(request.getDescripcion());

        TipoModel guardado = tipoRepository.save(tipo);
        log.info("Tipo guardado exitosamente con ID: {}", guardado.getId());

        return mapToResponse(guardado);
    }

    public List<TipoResponse> listarTodos() {
        log.info("Consultando todos los tipos registrados");
        return tipoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TipoResponse buscarPorId(Integer id) {
        log.info("Buscando tipo con ID: {}", id);
        TipoModel tipo = tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el tipo con ID: " + id));
        return mapToResponse(tipo);
    }

    private TipoResponse mapToResponse(TipoModel tipo) {
        return TipoResponse.builder()
                .id(tipo.getId())
                .nombre(tipo.getNombre())
                .descripcion(tipo.getDescripcion())
                .build();
    }
}