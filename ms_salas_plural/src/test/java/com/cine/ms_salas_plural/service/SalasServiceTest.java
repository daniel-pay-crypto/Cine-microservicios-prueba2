package com.cine.ms_salas_plural.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.cine.ms_salas_plural.dto.SalasDTO;
import com.cine.ms_salas_plural.model.Salas;
import com.cine.ms_salas_plural.repository.SalasRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SalasServiceTest {

    @Mock
    private SalasRepository salasRepository;

    // Mocks para simular el LoadBalancer y WebClient
    @Mock private WebClient.Builder webClientBuilderMock;
    @Mock private WebClient webClientMock;
    @SuppressWarnings("rawtypes")
    @Mock private WebClient.RequestHeadersUriSpec uriSpecMock;
    @SuppressWarnings("rawtypes")
    @Mock private WebClient.RequestHeadersSpec headersSpecMock;
    @Mock private WebClient.ResponseSpec responseSpecMock;

    @InjectMocks
    private SalasService salasService;

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
        // GIVEN programaciones en la base de datos (usando setters para evitar bugs de Lombok)
        Salas p1 = new Salas();
        p1.setId(1L);
        p1.setSalaId(10L);
        p1.setPeliculaId(100L);
        p1.setFechaInicio(LocalDateTime.now());

        Salas p2 = new Salas();
        p2.setId(2L);
        p2.setSalaId(20L);
        p2.setPeliculaId(200L);
        p2.setFechaInicio(LocalDateTime.now());

        when(salasRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // WHEN Ejecuta el método
        List<SalasDTO> resultado = salasService.listarTodas();

        // THEN Valida la respuesta
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(10L, resultado.get(0).getSalaId());
        verify(salasRepository, times(1)).findAll();
    }

    @Test
    void testCrearSalas_Exitoso() {
        // GIVEN DTO de entrada y simulación exitosa de la sala física
        SalasDTO dtoEntrada = new SalasDTO();
        dtoEntrada.setSalaId(15L);
        dtoEntrada.setPeliculaId(99L);
        
        Salas guardada = new Salas();
        guardada.setId(1L);
        guardada.setSalaId(15L);
        guardada.setPeliculaId(99L);
        guardada.setFechaInicio(LocalDateTime.now());

        simularWebClientExitoso();
        when(salasRepository.save(any(Salas.class))).thenReturn(guardada);

        // WHEN Guarda la programación
        SalasDTO resultado = salasService.crearSalas(dtoEntrada);

        // THEN Se guardo y mapeó correctamente
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(15L, resultado.getSalaId());
        verify(salasRepository, times(1)).save(any(Salas.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCrearSalas_FallaSalaFisicaNoExiste() {
        // GIVEN: Sala física inexistente (404 Not Found)
        SalasDTO dtoEntrada = new SalasDTO();
        dtoEntrada.setSalaId(999L);
        dtoEntrada.setPeliculaId(50L);

        when(webClientBuilderMock.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        
        WebClientResponseException.NotFound notFoundEx = mock(WebClientResponseException.NotFound.class);
        when(responseSpecMock.bodyToMono(Void.class)).thenThrow(notFoundEx);

        // WHEN & THEN: Falla rápido y protege la BD
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            salasService.crearSalas(dtoEntrada);
        });

        assertTrue(exception.getMessage().contains("La sala física con ID 999 no existe"));
        verify(salasRepository, never()).save(any(Salas.class));
    }

    @Test
    void testObtenerPorSalaId_Encontrado() {
        // GIVEN es la sala física que tiene programación
        Salas p1 = new Salas();
        p1.setId(1L);
        p1.setSalaId(10L);
        p1.setPeliculaId(100L);
        
        when(salasRepository.findBySalaId(10L)).thenReturn(Arrays.asList(p1));

        //WHEN
        List<SalasDTO> resultado = salasService.obtenerPorSalaId(10L);

        //THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getSalaId());
    }

    @Test
    void testObtenerPorSalaId_VacioLanzaExcepcion() {
        // GIVEN: Una búsqueda de sala física que no retorna nada
        when(salasRepository.findBySalaId(99L)).thenReturn(new ArrayList<>());

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            salasService.obtenerPorSalaId(99L);
        });

        assertEquals("Programación para la sala física con ID 99 no encontrada", exception.getMessage());
    }
}