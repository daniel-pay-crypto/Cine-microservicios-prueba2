package com.cine.ms_tickets.service;

import com.cine.ms_tickets.dto.TicketRequest;
import com.cine.ms_tickets.dto.TicketResponse;
import com.cine.ms_tickets.model.Ticket;
import com.cine.ms_tickets.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    void testGuardarTicket_Exitoso() {
        // GIVEN
        TicketRequest request = new TicketRequest();
        request.setPuesto("A-1");
        request.setPrecio(3000);
        request.setClienteId(1);
        request.setAsientoId(10);
        request.setPeliculaId(1);

        // Como usamos @SpringBootTest, el WebClientBuilder real está disponible.
        
        Ticket ticketPersistido = Ticket.builder().id(1).puesto("A-1").build();
        when(ticketRepository.existsByAsientoId(10)).thenReturn(false);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketPersistido);

        // WHEN
        TicketResponse resultado = ticketService.guardarTicket(request);

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }
}