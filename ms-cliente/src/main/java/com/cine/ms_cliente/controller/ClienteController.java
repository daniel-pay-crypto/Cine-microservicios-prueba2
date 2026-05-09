package com.cine.ms_cliente.controller;

import com.cine.ms_cliente.Client.TicketClient;
import com.cine.ms_cliente.dto.ClienteRequest;
import com.cine.ms_cliente.dto.ClienteResponse;
import com.cine.ms_cliente.dto.TicketDTO;
import com.cine.ms_cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    private TicketClient ticketClient;

    @GetMapping("/{id}")
    public ClienteResponse getCliente(@PathVariable Long id) {
    //  Buscas los datos del cliente en TU base de datos (puerto 8081)
    Cliente cliente = repository.findById(id).get(); 
    
    // Llamas al microservicio de tu amigo por OpenFeign (puerto 8085)
    List<TicketDTO> ticketsDelOtroServicio = ticketClient.getTicketsByClienteId(id);
    
    //  Mapeas todo a tu ClienteResponse
    ClienteResponse res = new ClienteResponse();
    res.setId(cliente.getId());
    res.setNombres(cliente.getNombres());
    res.setTickets(ticketsDelOtroServicio); // ¡AQUÍ SE UNEN LOS DOS MUNDOS!
    
    return res;
}
    // POST: /api/v1/clientes -> Crea un nuevo cliente
    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(@Valid @RequestBody ClienteRequest request) {
        // La anotación @Valid le dice a Spring que revise las reglas (@NotBlank, @Email) antes de entrar[cite: 9]
        log.info("Petición REST recibida para crear un nuevo cliente");
        ClienteResponse response = clienteService.guardarCliente(request);
        // Devolvemos un código 201 (CREATED) como dicta la pauta[cite: 9]
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }

    // GET: /api/v1/clientes -> Lista todos
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarClientes() {
        log.info("Petición REST recibida para listar todos los clientes");
        List<ClienteResponse> lista = clienteService.listarTodos();
        
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si está vacío[cite: 9]
        }
        return ResponseEntity.ok(lista);
    }

    // GET: /api/v1/clientes/{id} -> Busca por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarCliente(@PathVariable Integer id) {
        log.info("Petición REST recibida para buscar cliente por ID: {}", id);
        ClienteResponse response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}