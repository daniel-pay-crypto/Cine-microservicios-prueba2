package com.cine.ms_sucursales.service;

import com.cine.ms_sucursales.dto.SucursalDTO;
import com.cine.ms_sucursales.model.Sucursal;
import com.cine.ms_sucursales.repository.SucursalRepository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException; 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<SucursalDTO> listarTodas() {
        log.info("Obteniendo listado de todas las sucursales");
        return sucursalRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SucursalDTO guardar(SucursalDTO dto) {
        validarComuna(dto.getComunaid());

        log.info("Guardando nueva sucursal: {}", dto.getNombre());
        Sucursal sucursal = Sucursal.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .comunaId(dto.getComunaid())
                .build();

        Sucursal guardada = sucursalRepository.save(sucursal);
        return mapToDTO(guardada);
    }

    public SucursalDTO actualizar(Long id, SucursalDTO dtoDetalles) {
        log.info("Actualizando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        
        validarComuna(dtoDetalles.getComunaid());

        sucursal.setNombre(dtoDetalles.getNombre());
        sucursal.setDireccion(dtoDetalles.getDireccion());
        sucursal.setComunaId(dtoDetalles.getComunaid()); 
        
        return mapToDTO(sucursalRepository.save(sucursal));
    }

    public void eliminar(Long id) {
        log.warn("Eliminando sucursal ID: {}", id);
        if (!sucursalRepository.existsById(id)) {
            throw new RuntimeException("La sucursal con ID " + id + " no existe.");
        }
        sucursalRepository.deleteById(id);
    }

    public SucursalDTO buscarPorId(Long id) {
        log.info("Buscando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La sucursal con ID " + id + " no existe."));
        return mapToDTO(sucursal);
    }

    private SucursalDTO mapToDTO(Sucursal sucursal) {
        return SucursalDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .direccion(sucursal.getDireccion())
                .comunaid(sucursal.getComunaId())
                .build();
    }

    private void validarComuna(Long comunaId) {
        try {
            log.info("Verificando existencia de comuna ID: {} en ms-ubicacion usando WebClient...", comunaId);
            
            //hace la llamada directa al ms_ubicacion
            webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/api/v2/comunas/" + comunaId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                    
            log.info("Comuna validada con éxito.");
        } catch (WebClientResponseException.NotFound e) {
            log.error("La comuna ID {} no existe.", comunaId);
            throw new RuntimeException("Error: No se puede proceder porque la Comuna con ID " + comunaId + " no existe.");
        } catch (WebClientResponseException e) {
            log.error("Error de comunicación con ms-ubicacion: {}", e.getMessage());
            throw new RuntimeException("Error interno al validar la comuna. Intente más tarde.");
        }
    }
}