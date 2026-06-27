package com.cine.ms_salas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.cine.ms_salas.dto.SalaDTO;
import com.cine.ms_salas.model.Sala;
import com.cine.ms_salas.repository.SalaRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @Mock private WebClient.Builder webClientBuilderMock;
    @Mock private WebClient webClientMock;
    @SuppressWarnings("rawtypes")
    @Mock private WebClient.RequestHeadersUriSpec uriSpecMock;
    @SuppressWarnings("rawtypes")
    @Mock private WebClient.RequestHeadersSpec headersSpecMock;
    @Mock private WebClient.ResponseSpec responseSpecMock;

    @InjectMocks
    private SalaService salaService;

    @BeforeEach
    void setUp() {
    }

    @SuppressWarnings("unchecked")
    private void simularWebClientExitoso() {
        when(webClientBuilderMock.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(Void.class)).thenReturn(Mono.empty());
    }

    @Test
    void testListarTodas() {
        Sala sala1 = new Sala(1L, "Sala 1", 50, 2L, 1);
        Sala sala2 = new Sala(2L, "Sala IMAX", 120, 2L, 2);
        when(salaRepository.findAll()).thenReturn(Arrays.asList(sala1, sala2));

        List<SalaDTO> resultado = salaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Sala IMAX", resultado.get(1).getNombre());
        verify(salaRepository, times(1)).findAll();
    }

    @Test
    void testGuardar_Exitoso() {
        SalaDTO dtoEntrada = new SalaDTO(null, "Sala VIP", 30, 1L, 1);
        Sala salaGuardada = new Sala(1L, "Sala VIP", 30, 1L, 1);
        
        simularWebClientExitoso();
        when(salaRepository.save(any(Sala.class))).thenReturn(salaGuardada);

        SalaDTO resultado = salaService.guardar(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sala VIP", resultado.getNombre());
        verify(salaRepository, times(1)).save(any(Sala.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGuardar_FallaPorTipoNoEncontrado() {
        SalaDTO dtoEntrada = new SalaDTO(null, "Sala Rota", 50, 1L, 999);
        
        when(webClientBuilderMock.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        
        WebClientResponseException.NotFound notFoundEx = mock(WebClientResponseException.NotFound.class);
        when(responseSpecMock.bodyToMono(Void.class)).thenThrow(notFoundEx);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            salaService.guardar(dtoEntrada);
        });

        assertTrue(exception.getMessage().contains("El tipo ID 999 no existe"));
        verify(salaRepository, never()).save(any(Sala.class));
    }
}