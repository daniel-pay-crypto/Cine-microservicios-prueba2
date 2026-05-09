package com.cine.ms_cliente.Client;

import com.cine.ms_cliente.dto.TicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

//ms-ticket es solo referencial pero la URL es la que importa CAMBIAR EL PUERTO IMPORTANT EN DS LLAMADA
@FeignClient(name = "ms-ticket", url = "http://localhost:8085/api/tickets")
public interface TicketClient {

    @GetMapping("/cliente/{clienteId}")
    List<TicketDTO> getTicketsByClienteId(@PathVariable("clienteId") Long clienteId);

}
