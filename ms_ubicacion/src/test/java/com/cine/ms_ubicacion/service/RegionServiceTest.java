package com.cine.ms_ubicacion.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cine.ms_ubicacion.dto.RegionDTO;
import com.cine.ms_ubicacion.model.Region;
import com.cine.ms_ubicacion.repository.RegionRepository;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    @Test
    void testListarTodas() {
        Region r1 = new Region();
        r1.setId(1L);
        r1.setNombre("Metropolitana");

        Region r2 = new Region();
        r2.setId(2L);
        r2.setNombre("Valparaíso");

        when(regionRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<RegionDTO> resultado = regionService.listarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Metropolitana", resultado.get(0).getNombre());
        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        RegionDTO dtoEntrada = new RegionDTO(null, "Araucanía");
        
        Region guardada = new Region();
        guardada.setId(1L);
        guardada.setNombre("Araucanía");

        when(regionRepository.save(any(Region.class))).thenReturn(guardada);

        RegionDTO resultado = regionService.guardar(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Araucanía", resultado.getNombre());
        verify(regionRepository, times(1)).save(any(Region.class));
    }
}