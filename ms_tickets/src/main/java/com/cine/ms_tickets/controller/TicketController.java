package com.cine.ms_tickets.controller;

import com.cine.ms_tickets.dto.TicketRequest;
import com.cine.ms_tickets.dto.TicketResponse;
import com.cine.ms_tickets.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> crearTicket(@Valid @RequestBody TicketRequest request) {
        log.info("Peticion REST recibida para emitir un nuevo ticket para el asiento: {}", request.getPuesto());
        TicketResponse response = ticketService.guardarTicket(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> listarTickets() {
        log.info("Peticion REST recibida para listar todos los tickets");
        List<TicketResponse> lista = ticketService.listarTodos();
        
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> buscarTicket(@PathVariable Integer id) {
        log.info("Peticion REST recibida para buscar ticket por ID: {}", id);
        TicketResponse response = ticketService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}