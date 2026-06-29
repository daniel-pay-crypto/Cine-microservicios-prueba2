package com.cine.ms_cliente.service;

import com.cine.ms_cliente.dto.ClienteDetalleDTO;
import com.cine.ms_cliente.dto.TicketDTO;
import com.cine.ms_cliente.model.Cliente;
import com.cine.ms_cliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest

@ActiveProfiles("test") 
public class ClienteServiceIntegrationTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @MockBean
    private WebClient.Builder webClientBuilder;

    @MockBean
    private WebClient webClient;
    
    @SuppressWarnings("rawtypes")
    @MockBean
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    
    @SuppressWarnings("rawtypes")
    @MockBean 
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    
    @MockBean
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    // Prueba: 1
    @Test
    void testIntegracionGuardarCliente() {
        // Usamos los datos de la base de datos
        Cliente cliente = new Cliente(null, "Juan", "Pérez", "juan.perez@example.com");
        Cliente guardado = clienteService.guardar(cliente);
        
        assertNotNull(guardado.getId()); 
        assertEquals("Juan", guardado.getNombre());
        assertEquals("juan.perez@example.com", guardado.getEmail());
    }

    // Prueba: 2
    @Test
    void testIntegracionBuscarPorId() {
        // Usamos los datos de la base de datos
        Cliente cliente = new Cliente(null, "María", "Gómez", "maria.gomez@example.com");
        Cliente guardado = clienteRepository.save(cliente); 
        
        Cliente encontrado = clienteService.buscarPorId(guardado.getId());
        
        assertNotNull(encontrado);
        assertEquals("maria.gomez@example.com", encontrado.getEmail());
    }

    // Prueba: 3
    @Test
    void testIntegracionObtenerClienteConTickets() {
        // Uso de mi BD Real en este caso (Combinando nombres para probar algo nuevo)
        Cliente cliente = new Cliente(null, "Juan", "Pérez", "juan.perez@example.com");
        Cliente guardado = clienteRepository.save(cliente);


        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);


        TicketDTO ticket = new TicketDTO();
        ticket.setPrecio(2500.0);
        TicketDTO[] mockTickets = {ticket};
        when(responseSpec.bodyToMono(TicketDTO[].class)).thenReturn(Mono.just(mockTickets));



        // Ejecuto y valido...
        ClienteDetalleDTO resultado = clienteService.obtenerClienteConTickets(guardado.getId());
        
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
        assertEquals(1, resultado.getTickets().size());
    }
}