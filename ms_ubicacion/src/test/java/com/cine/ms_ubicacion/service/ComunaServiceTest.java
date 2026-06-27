package com.cine.ms_ubicacion.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cine.ms_ubicacion.dto.ComunaDTO;
import com.cine.ms_ubicacion.model.Comuna;
import com.cine.ms_ubicacion.model.Region;
import com.cine.ms_ubicacion.repository.ComunaRepository;
import com.cine.ms_ubicacion.repository.RegionRepository;

@ExtendWith(MockitoExtension.class)
class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private ComunaService comunaService;

    @Test
    void testListarTodas() {
        Region regionMock = new Region();
        regionMock.setId(1L);
        regionMock.setNombre("Metropolitana");

        Comuna c1 = new Comuna();
        c1.setId(1L);
        c1.setNombre("Santiago");
        c1.setRegion(regionMock);

        when(comunaRepository.findAll()).thenReturn(Arrays.asList(c1));

        List<ComunaDTO> resultado = comunaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Santiago", resultado.get(0).getNombre());
        assertEquals("Metropolitana", resultado.get(0).getNombreRegion());
        verify(comunaRepository, times(1)).findAll();
    }

    @Test
    void testGuardar_Exitoso() {
        Region regionMock = new Region();
        regionMock.setId(1L);
        regionMock.setNombre("Biobío");

        ComunaDTO dtoEntrada = new ComunaDTO(null, "Concepción", null);
        
        Comuna guardada = new Comuna();
        guardada.setId(10L);
        guardada.setNombre("Concepción");
        guardada.setRegion(regionMock);

        when(regionRepository.findById(1L)).thenReturn(Optional.of(regionMock));
        when(comunaRepository.save(any(Comuna.class))).thenReturn(guardada);

        ComunaDTO resultado = comunaService.guardar(dtoEntrada, 1L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Concepción", resultado.getNombre());
        assertEquals("Biobío", resultado.getNombreRegion());
        verify(regionRepository, times(1)).findById(1L);
        verify(comunaRepository, times(1)).save(any(Comuna.class));
    }

    @Test
    void testGuardar_FallaPorRegionNoEncontrada() {
        ComunaDTO dtoEntrada = new ComunaDTO(null, "Rancagua", null);

        when(regionRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            comunaService.guardar(dtoEntrada, 99L);
        });

        assertTrue(exception.getMessage().contains("La Región con ID 99 no existe"));
        verify(comunaRepository, never()).save(any(Comuna.class));
    }

    @Test
    void testBuscarPorId_Exitoso() {
        Region regionMock = new Region();
        regionMock.setNombre("Araucanía");

        Comuna c1 = new Comuna();
        c1.setId(5L);
        c1.setNombre("Temuco");
        c1.setRegion(regionMock);

        when(comunaRepository.findById(5L)).thenReturn(Optional.of(c1));

        ComunaDTO resultado = comunaService.buscarPorId(5L);

        assertNotNull(resultado);
        assertEquals("Temuco", resultado.getNombre());
        assertEquals("Araucanía", resultado.getNombreRegion());
        verify(comunaRepository, times(1)).findById(5L);
    }
}