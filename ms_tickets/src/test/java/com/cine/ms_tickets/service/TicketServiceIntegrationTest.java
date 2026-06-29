package com.cine.ms_tickets.service;

import com.cine.ms_tickets.dto.TicketRequest;
import com.cine.ms_tickets.dto.TicketResponse;
import com.cine.ms_tickets.repository.TicketRepository;
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
public class TicketServiceIntegrationTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;


    //Mockeamos todo como buenos resfriados... chiste pa malo
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
        // Limpiamos la BD
        ticketRepository.deleteAll();
    }

   //prueba de integracion para guardar ticket
    @Test
    void testIntegracionGuardarTicket() {
        // 1. Preparamos el Request con datos de prueba
        TicketRequest request = new TicketRequest();
        request.setPuesto("C3");
        request.setPrecio(4500.0);
        request.setClienteId(1);
        request.setAsientoId(15);
        request.setPeliculaId(3);

        //aca simulo que ms-cliente nos responde un 200 OK

        when(webClientBuilder.build()).thenReturn(webClient);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        // ejecuto el método que interactúa con la Base de Datos
        TicketResponse resultado = ticketService.guardarTicket(request);


        // valido aca  que el resultado no sea nulo y que se guardó en la BD real
        assertNotNull(resultado);
        assertEquals("C3", resultado.getPuesto());
        

        // verifico que en la BD real que ahora hay 1 ticket guardado...
        assertEquals(1, ticketRepository.findAll().size()); 
    }
}