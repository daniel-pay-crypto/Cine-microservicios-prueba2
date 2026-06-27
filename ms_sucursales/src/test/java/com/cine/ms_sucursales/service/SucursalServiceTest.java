package com.cine.ms_sucursales.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.cine.ms_sucursales.dto.SucursalDTO;
import com.cine.ms_sucursales.model.Sucursal;
import com.cine.ms_sucursales.repository.SucursalRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock private WebClient.Builder webClientBuilderMock;
    @Mock private WebClient webClientMock;
    @SuppressWarnings("rawtypes")
    @Mock private WebClient.RequestHeadersUriSpec uriSpecMock;
    @SuppressWarnings("rawtypes")
    @Mock private WebClient.RequestHeadersSpec headersSpecMock;
    @Mock private WebClient.ResponseSpec responseSpecMock;

    @InjectMocks
    private SucursalService sucursalService;

    @SuppressWarnings("unchecked")
    private void simularWebClientExitoso() {
        when(webClientBuilderMock.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(Object.class)).thenReturn(Mono.empty());
    }

    @Test
    void testListarTodas() {
        Sucursal s1 = Sucursal.builder().id(1L).nombre("Centro").direccion("Calle 1").comunaId(10L).build();
        Sucursal s2 = Sucursal.builder().id(2L).nombre("Norte").direccion("Calle 2").comunaId(20L).build();
        when(sucursalRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<SucursalDTO> resultado = sucursalService.listarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Norte", resultado.get(1).getNombre());
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    void testGuardar_Exitoso() {
        SucursalDTO dtoEntrada = SucursalDTO.builder().nombre("Plaza").direccion("Avenida 3").comunaid(30L).build();
        Sucursal guardada = Sucursal.builder().id(1L).nombre("Plaza").direccion("Avenida 3").comunaId(30L).build();

        simularWebClientExitoso();
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(guardada);

        SucursalDTO resultado = sucursalService.guardar(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Plaza", resultado.getNombre());
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGuardar_FallaPorComunaNoEncontrada() {
        SucursalDTO dtoEntrada = SucursalDTO.builder().nombre("Fantasma").direccion("Nula").comunaid(999L).build();

        when(webClientBuilderMock.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);

        WebClientResponseException.NotFound notFoundEx = mock(WebClientResponseException.NotFound.class);
        when(responseSpecMock.bodyToMono(Object.class)).thenThrow(notFoundEx);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sucursalService.guardar(dtoEntrada);
        });

        assertTrue(exception.getMessage().contains("la Comuna con ID 999 no existe"));
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }

    @Test
    void testActualizar_Exitoso() {
        Sucursal existente = Sucursal.builder().id(1L).nombre("Viejo").direccion("Antigua").comunaId(10L).build();
        SucursalDTO dtoActualizar = SucursalDTO.builder().nombre("Nuevo").direccion("Moderna").comunaid(10L).build();
        Sucursal actualizada = Sucursal.builder().id(1L).nombre("Nuevo").direccion("Moderna").comunaId(10L).build();

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(existente));
        simularWebClientExitoso();
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(actualizada);

        SucursalDTO resultado = sucursalService.actualizar(1L, dtoActualizar);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("Moderna", resultado.getDireccion());
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    void testEliminar_Exitoso() {
        when(sucursalRepository.existsById(1L)).thenReturn(true);
        doNothing().when(sucursalRepository).deleteById(1L);

        assertDoesNotThrow(() -> sucursalService.eliminar(1L));
        verify(sucursalRepository, times(1)).deleteById(1L);
    }

    @Test
    void testBuscarPorId_Exitoso() {
        Sucursal s1 = Sucursal.builder().id(1L).nombre("Centro").direccion("Calle 1").comunaId(10L).build();
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(s1));

        SucursalDTO resultado = sucursalService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Centro", resultado.getNombre());
        verify(sucursalRepository, times(1)).findById(1L);
    }
}