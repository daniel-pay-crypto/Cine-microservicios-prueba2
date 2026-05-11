package com.cine.ms_sucursales.service;

import com.cine.ms_sucursales.client.ComunaClient;
import com.cine.ms_sucursales.dto.SucursalDTO;
import com.cine.ms_sucursales.model.Sucursal;
import com.cine.ms_sucursales.repository.SucursalRepository;
import feign.FeignException; 
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;
    private final ComunaClient comunaClient; 

    public List<SucursalDTO> listarTodas() {
        log.info("Obteniendo listado de todas las sucursales");
        return sucursalRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SucursalDTO guardar(Sucursal sucursal) {
        // 1. VALIDACIÓN CON OPENFEIGN
        validarComuna(sucursal.getComunaId());

        // 2. GUARDADO
        log.info("Guardando nueva sucursal: {}", sucursal.getNombre());
        Sucursal guardada = sucursalRepository.save(sucursal);
        return mapToDTO(guardada);
    }

    public SucursalDTO actualizar(Long id, Sucursal detalles) {
        // 1. VALIDAR SI LA SUCURSAL EXISTE
        log.info("Actualizando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        
        // 2. VALIDAR LA COMUNA NUEVA CON OPENFEIGN
        validarComuna(detalles.getComunaId());

        // 3. ACTUALIZAR Y GUARDAR
        sucursal.setNombre(detalles.getNombre());
        sucursal.setDireccion(detalles.getDireccion());
        sucursal.setComunaId(detalles.getComunaId()); 
        
        return mapToDTO(sucursalRepository.save(sucursal));
    }

    public void eliminar(Long id) {
        log.warn("Eliminando sucursal ID: {}", id);
        sucursalRepository.deleteById(id);
    }

    private SucursalDTO mapToDTO(Sucursal sucursal) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(sucursal.getId());
        dto.setNombre(sucursal.getNombre());
        dto.setDireccion(sucursal.getDireccion());
        dto.setComunaid(sucursal.getComunaId()); 
        return dto;
    }

    // MÉTODO PRIVADO PARA REUTILIZAR LA VALIDACIÓN
    private void validarComuna(Long comunaId) {
        try {
            log.info("Verificando existencia de comuna ID: {} en ms-ubicacion...", comunaId);
            comunaClient.obtenerPorId(comunaId);
            log.info("Comuna validada con éxito.");
        } catch (FeignException.NotFound e) {
            log.error("La comuna ID {} no existe.", comunaId);
            throw new RuntimeException("Error: No se puede proceder porque la Comuna con ID " + comunaId + " no existe.");
        } catch (FeignException e) {
            log.error("Error de comunicación con ms-ubicacion: {}", e.getMessage());
            throw new RuntimeException("Error interno al validar la comuna. Intente más tarde.");
        }
    }
}