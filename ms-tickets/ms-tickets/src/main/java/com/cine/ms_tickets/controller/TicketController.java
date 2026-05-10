package com.cine.ms_tickets.controller;

import com.cine.ms_tickets.dto.TicketRequest;
import com.cine.ms_tickets.dto.TicketResponse;
import com.cine.ms_tickets.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // POST: /api/v1/tickets -> Crea un nuevo ticket
    @PostMapping
    public ResponseEntity<TicketResponse> crearTicket(@Valid @RequestBody TicketRequest request) {
        // @Valid asegurará que el asiento no venga vacío y el precio sea correcto según el DTO
        log.info("Petición REST recibida para emitir un nuevo ticket");
        TicketResponse response = ticketService.guardarTicket(request);
        // Devolvemos un código 201 (CREATED)
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }

    // GET: /api/v1/tickets -> Lista todos los tickets vendidos
    @GetMapping
    public ResponseEntity<List<TicketResponse>> listarTickets() {
        log.info("Petición REST recibida para listar todos los tickets");
        List<TicketResponse> lista = ticketService.listarTodos();
        
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si aún no hay ventas
        }
        return ResponseEntity.ok(lista); // 200 OK
    }

    // GET: /api/v1/tickets/{id} -> Busca un ticket específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> buscarTicket(@PathVariable Integer id) {
        log.info("Petición REST recibida para buscar ticket por ID: {}", id);
        TicketResponse response = ticketService.buscarPorId(id);
        return ResponseEntity.ok(response); // 200 OK si lo encuentra
    }
}