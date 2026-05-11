package com.cine.ms_cliente.service;

import com.cine.ms_cliente.client.TicketFeignClient;
import com.cine.ms_cliente.dto.ClienteDetalleDTO;
import com.cine.ms_cliente.dto.TicketDTO;
import com.cine.ms_cliente.model.Cliente;
import com.cine.ms_cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

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

    public Cliente buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

}