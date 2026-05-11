package com.cine.ms_cliente.client;

import com.cine.ms_cliente.dto.TicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "ms-tickets", url = "http://localhost:8090/api/v1/tickets")
public interface TicketFeignClient {
    
    @GetMapping("/cliente/{clienteId}")
    List<TicketDTO> obtenerTickets(@PathVariable("clienteId") Long clienteId);
}