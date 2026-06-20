package com.cine.ms_tickets.service;

import com.cine.ms_tickets.dto.TicketRequest;
import com.cine.ms_tickets.dto.TicketResponse;
import com.cine.ms_tickets.model.Ticket;
import com.cine.ms_tickets.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketService {

    //variable privada para registrar mensajes de log EYEE
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

//METODOS DE LA LOGICA DE NEGOCIO








    //Metodo publocio para guardar un ticket, valida que el cliente exista y que el asiento no este ocupado.
    public TicketResponse guardarTicket(TicketRequest request) {
        log.info("Creando ticket");

        // VALIDO AL CLIENTE MEDIANTE WEBCLIENT USANDO EUREKA
        try {
            log.info("Validando que el cliente ID {} exista...", request.getClienteId());
            webClientBuilder.build()
                    .get()
                    .uri("http://ms-cliente/api/v1/clientes/" + request.getClienteId())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (WebClientResponseException.NotFound e) { 
            log.error("Error: El cliente con el ID {} no existe.", request.getClienteId());
            throw new RuntimeException("Error: El cliente con el ID " + request.getClienteId() + " no existe.");
        
        } catch (Exception e) { 
            log.error("Error de comunicación con Ms-Cliente: {}", e.getMessage());
            throw new RuntimeException("Error en la comunicación con el service de clientes, Lamentamos las molestias.");
        }

        // Aplico la regla de negocio de no vender un asiento ya ocupado
        if (ticketRepository.existsByAsientoId(request.getAsientoId())) {
            log.error("El asiento {} se encuentra ocupado", request.getAsientoId());
            throw new RuntimeException("Error: Lo sentimos, el asiento ya ha sido vendido.");
        }

        Ticket ticket = Ticket.builder()
                .puesto(request.getPuesto())
                .precio(request.getPrecio())
                .clienteId(request.getClienteId())
                .peliculaId(request.getPeliculaId())
                .asientoId(request.getAsientoId())
                .build();

        Ticket guardado = ticketRepository.save(ticket);
        log.info("Ticket emitido con exito, su ID es: {}", guardado.getId());
        return mapToResponse(guardado);
    }

    //Metodo publico para listar todos los tickets emitidos
    public List<TicketResponse> listarTodos() {
        return ticketRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //Metodo publico para buscar un ticket por su ID
    public TicketResponse buscarPorId(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con el ID: " + id)); 
        return mapToResponse(ticket);
    }


    //Metodo publico para listar todos los tickets emitidos a un cliente en especifico
    public List<TicketResponse> listarPorCliente(Integer clienteId) {
        return ticketRepository.findByClienteId(clienteId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }



    //Metodo privado para mapear de Ticket a TicketResponse
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