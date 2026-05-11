package com.cine.ms_tickets.service;

import com.cine.ms_tickets.client.ClienteClient; 
import com.cine.ms_tickets.dto.TicketRequest;
import com.cine.ms_tickets.dto.TicketResponse;
import com.cine.ms_tickets.model.Ticket;
import com.cine.ms_tickets.repository.TicketRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ClienteClient clienteClient;

    public TicketResponse guardarTicket(TicketRequest request) {
        log.info("Iniciando proceso de emision de ticket");

        // --- 1. VALIDACIÓN DE CLIENTE MEDIANTE FEIGN ---
        try {
            log.info("Validando que el cliente ID {} exista...", request.getClienteId());
            clienteClient.buscarPorId(request.getClienteId());
        } catch (FeignException.NotFound e) {
            log.error("Error: El cliente con ID {} no existe.", request.getClienteId());
            throw new RuntimeException("Error: El cliente con ID " + request.getClienteId() + " no existe.");
        } catch (FeignException e) {
            log.error("Error de comunicación con ms-cliente: {}", e.getMessage());
            throw new RuntimeException("Error de comunicación con el servicio de clientes.");
        }

        // --- 2. Regla de negocio: No vender un asiento ya ocupado ---
        if (ticketRepository.existsByAsientoId(request.getAsientoId())) {
            log.error("El asiento {} ya esta ocupado", request.getAsientoId());
            throw new RuntimeException("Error: El asiento ya ha sido vendido.");
        }

        Ticket ticket = Ticket.builder()
                .puesto(request.getPuesto())
                .precio(request.getPrecio())
                .clienteId(request.getClienteId())
                .peliculaId(request.getPeliculaId())
                .asientoId(request.getAsientoId())
                .build();

        Ticket guardado = ticketRepository.save(ticket);
        log.info("Ticket emitido con exito, ID: {}", guardado.getId());
        return mapToResponse(guardado);
    }

    public List<TicketResponse> listarTodos() {
        return ticketRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TicketResponse buscarPorId(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
        return mapToResponse(ticket);
    }

    public List<TicketResponse> listarPorCliente(Integer clienteId) {
        return ticketRepository.findByClienteId(clienteId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .puesto(ticket.getPuesto())
                .precio(ticket.getPrecio())
                .clienteId(ticket.getClienteId())
                .peliculaId(ticket.getPeliculaId())
                .asientoId(ticket.getAsientoId())
                .build();
    }
}