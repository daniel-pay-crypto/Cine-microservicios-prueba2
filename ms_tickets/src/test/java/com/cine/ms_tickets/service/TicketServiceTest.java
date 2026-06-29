package com.cine.ms_tickets.service;

import com.cine.ms_tickets.dto.TicketRequest;
import com.cine.ms_tickets.dto.TicketResponse;
import com.cine.ms_tickets.model.Ticket;
import com.cine.ms_tickets.repository.TicketRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private TicketService ticketService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void testGuardarTicket_Exitoso() {
        // datos falsos para la petición
        Integer clienteIdSimulado = faker.number().randomDigitNotZero();
        Integer asientoIdSimulado = faker.number().randomDigitNotZero();
        
        TicketRequest request = new TicketRequest();
        request.setPuesto(faker.letterify("?#"));

        request.setPrecio((double) faker.number().numberBetween(1000, 5000));
        request.setClienteId(clienteIdSimulado);

        request.setAsientoId(asientoIdSimulado);
        request.setPeliculaId(faker.number().randomDigitNotZero());



        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty()); // Mono.empty() simula Void exitoso




        // Simulo que el asiento no está ocupado en la base de datos:
        when(ticketRepository.existsByAsientoId(asientoIdSimulado)).thenReturn(false);

        // Simulo el guardado en el repositorio:
        Ticket ticketGuardado = Ticket.builder()
        
                .id(faker.number().randomDigitNotZero())
                .puesto(request.getPuesto())
                .precio(request.getPrecio())

                .clienteId(request.getClienteId())

                .asientoId(request.getAsientoId())
                .peliculaId(request.getPeliculaId())
                .build();
                
        // Usamos any(Ticket.class) porque dentro del servicio se construye una nueva instancia de Ticket
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketGuardado);

        // WHEN: ejecuto el método a probar
        TicketResponse resultado = ticketService.guardarTicket(request);

        // THEN: validaciones básicas de que la lógica funciono:
        assertNotNull(resultado);
        assertEquals(request.getPuesto(), resultado.getPuesto());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test

    void testGuardarTicket_ErrorAsientoOcupado() {

        // GIVEN: un asiento que ya existe en Mi sistema:
        Integer asientoOcupado = 55;
        TicketRequest request = new TicketRequest();
        request.setClienteId(1);
        request.setAsientoId(asientoOcupado);



        // Mocks del WebClient para que la validación de cliente pase bien
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());



        // entreno al repositorio para que diga que el asiento si esta ocupado
        when(ticketRepository.existsByAsientoId(asientoOcupado)).thenReturn(true);

        
        // WHEN & THEN: verifico que lance la excepción esperada:
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.guardarTicket(request);
        });


        assertEquals("Error: Lo sentimos, el asiento ya ha sido vendido.", exception.getMessage());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }
}