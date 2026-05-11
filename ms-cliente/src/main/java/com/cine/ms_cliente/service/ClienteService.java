package com.cine.ms_cliente.service;

import com.cine.ms_cliente.dto.ClienteDetalleDTO;
import com.cine.ms_cliente.dto.TicketDTO;
import com.cine.ms_cliente.model.Cliente;
import com.cine.ms_cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Servicio para manejar la lógica de negocio relacionada con Clientes
@Service
@RequiredArgsConstructor
public class ClienteService {

    // Manejo de Logs estructurados (SLF4J)
    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository repository;
    private final TicketFeignClient ticketFeign;

    public ClienteDetalleDTO obtenerClienteConTickets(Long id) {
        log.info("Buscando cliente con id: {}", id);
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        log.info("Llamando a ms-tickets a través de Feign para el cliente id: {}", id);
        List<TicketDTO> tickets = ticketFeign.obtenerTickets(id);

        ClienteDetalleDTO dto = new ClienteDetalleDTO();
        dto.setId(cliente.getId());
        dto.setNombreCompleto(cliente.getNombre() + " " + cliente.getApellido());
        dto.setTickets(tickets);
        
        log.info("Retornando cliente con {} tickets", tickets.size());
        return dto;
    }

    public Cliente guardar(Cliente cliente) {
        log.info("Guardando nuevo cliente en la base de datos");
        return repository.save(cliente);
    }
}

// Interfaz Feign Cliente
@FeignClient(name = "ms-tickets", url = "http://localhost:8089/api/v1/tickets")
interface TicketFeignClient {
    @GetMapping("/cliente/{clienteId}")
    List<TicketDTO> obtenerTickets(@PathVariable("clienteId") Long clienteId);
}