package com.cine.ms_cliente.service;

import com.cine.ms_cliente.dto.ClienteDetalleDTO;
import com.cine.ms_cliente.dto.TicketDTO;
import com.cine.ms_cliente.model.Cliente;
import com.cine.ms_cliente.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono; //EYE

// Servicio para manejar la lógica de negocio relacionada con Clientes (Cerebro de Ms-Clientes)
@Service
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ClienteDetalleDTO obtenerClienteConTickets(Long id) {
        log.info("Buscando cliente con el id: {}", id);
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado u no existe."));

        ClienteDetalleDTO dto = new ClienteDetalleDTO();
        dto.setId(cliente.getId());
        dto.setNombreCompleto(cliente.getNombre() + " " + cliente.getApellido());

        try {
            log.info("...Iniciando transmisión mediante WebClient hacia Ms-Tickets...");
            // Capturamos la lista como un Array y la convertimos a una Lista, esto es porque WebClient no puede convertir directamente a List, pero si a Array.
            TicketDTO[] ticketsArray = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/api/v1/tickets/cliente/" + cliente.getId())
                    .retrieve()
                    .bodyToMono(TicketDTO[].class)
                    .block(); // El .block() es lo que hace que espere la respuesta de ms-tickets, es como decir "espera un momento, que estoy hablando con ms-tickets".

           //Aca se combierte el Array a una Lista, si es null, se asigna una lista vacía para evitar problemas (asi lo hacesmos de forma segura)        
            dto.setTickets(ticketsArray != null ? java.util.Arrays.asList(ticketsArray) : new java.util.ArrayList<>());
            
        } catch (Exception e) {
            log.error("Lamentamos informar que hay problemas de conexión con Ms-Tickets: {}", e.getMessage());
            // Si falla, le mando una lista que viene vacía para no romper el programa.
            dto.setTickets(new java.util.ArrayList<>()); 
        }

        return dto;
    }

    public Cliente guardar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
    }

}