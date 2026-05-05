package Com.cine.ms_sucursales.service;

import Com.cine.ms_sucursales.DTO.SucursalDTO;
import Com.cine.ms_sucursales.model.Sucursal;
import Com.cine.ms_sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor//reemplaza al @Autowired manual
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    // 1. Listar todas (Convirtiendo a DTO)
    public List<SucursalDTO> listarTodas() {
        log.info("Obteniendo listado de todas las sucursales");
        return sucursalRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 2. Guardar nueva
    public SucursalDTO guardar(Sucursal sucursal) {
        log.info("Guardando nueva sucursal: {}", sucursal.getNombre());
        Sucursal guardada = sucursalRepository.save(sucursal);
        return mapToDTO(guardada);
    }

    // 3. Actualizar
    public SucursalDTO actualizar(Long id, Sucursal detalles) {
        log.info("Actualizando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrada"));
        
        sucursal.setNombre(detalles.getNombre());
        sucursal.setDireccion(detalles.getDireccion());
        
        return mapToDTO(sucursalRepository.save(sucursal));
    }

    // 4. Eliminar
    public void eliminar(Long id) {
        log.warn("Eliminando sucursal ID: {}", id);
        sucursalRepository.deleteById(id);
    }

    // Método utilitario para convertir (Mapeo)
    private SucursalDTO mapToDTO(Sucursal sucursal) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(sucursal.getId());
        dto.setNombre(sucursal.getNombre());
        dto.setDireccion(sucursal.getDireccion());
        return dto;
    }
}