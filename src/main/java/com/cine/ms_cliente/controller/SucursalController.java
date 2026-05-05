package Com.cine.ms_sucursales.controller;

import Com.cine.ms_sucursales.DTO.SucursalDTO;
import Com.cine.ms_sucursales.model.Sucursal;
import Com.cine.ms_sucursales.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor 
public class SucursalController {


    private final SucursalService sucursalService; 

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> listarTodas() {
        log.info("Recibida petición GET para listar todas las sucursales");
        return ResponseEntity.ok(sucursalService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> crear(@Valid @RequestBody Sucursal sucursal) {
        log.info("Recibida petición POST para crear sucursal: {}", sucursal.getNombre());
        SucursalDTO nuevaSucursal = sucursalService.guardar(sucursal);
        return new ResponseEntity<>(nuevaSucursal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizar(@PathVariable Long id, @Valid @RequestBody Sucursal sucursalDetalles) {
        log.info("Recibida petición PUT para actualizar sucursal con ID: {}", id);
        SucursalDTO sucursalActualizada = sucursalService.actualizar(id, sucursalDetalles);
        return ResponseEntity.ok(sucursalActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Recibida petición DELETE para eliminar sucursal con ID: {}", id);
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}