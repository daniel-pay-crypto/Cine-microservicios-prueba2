package com.cine.ms_tickets.controller;

import com.cine.ms_tickets.dto.TicketRequest;
import com.cine.ms_tickets.dto.TicketResponse;
import com.cine.ms_tickets.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
@Tag(name = "Tickets", description = "Operaciones de emision y busqueda de tickets del cine")
public class TicketController {


    private static final Logger log = LoggerFactory.getLogger(TicketController.class);


    @Autowired
    private TicketService ticketService;

    @PostMapping
    @Operation(summary = "Emitir un nuevo ticket", description = "Valida al cliente y guarda un nuevo ticket en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "¡Ticket emitido exitosamente!"),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida o asiento ocupado")
    })
    public ResponseEntity<EntityModel<TicketResponse>> crearTicket(@Valid @RequestBody TicketRequest request) {
        log.info("Peticion REST recibida para emitir un nuevo ticket para el asiento: {}", request.getPuesto());
        TicketResponse response = ticketService.guardarTicket(request);

        // HATEOAS 
        EntityModel<TicketResponse> recurso = EntityModel.of(response);
        recurso.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).buscarTicket(response.getId())).withSelfRel());
        recurso.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).listarTickets()).withRel("todos-los-tickets"));

        return new ResponseEntity<>(recurso, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los tickets", description = "Retorna la lista completa de tickets vendidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay tickets registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<TicketResponse>>> listarTickets() {
        log.info("Peticion REST recibida para listar todos los tickets");
        List<TicketResponse> lista = ticketService.listarTodos();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // HATEOAS PARA LISTAS DE TICKETS
        List<EntityModel<TicketResponse>> ticketsRecursos = lista.stream()
                .map(ticket -> EntityModel.of(ticket,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).buscarTicket(ticket.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TicketResponse>> coleccion = CollectionModel.of(ticketsRecursos);
        coleccion.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).listarTickets()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ticket por ID", description = "Retorna el detalle de un ticket especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    public ResponseEntity<EntityModel<TicketResponse>> buscarTicket(
            @Parameter(description = "ID del ticket a buscar", example = "1") @PathVariable Integer id) {
        
        log.info("Peticion REST recibida para buscar ticket por ID: {}", id);
        TicketResponse response = ticketService.buscarPorId(id);

        // HATEOAS para un solo ticket
        EntityModel<TicketResponse> recurso = EntityModel.of(response);
        recurso.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).buscarTicket(id)).withSelfRel());
        recurso.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).listarTickets()).withRel("todos-los-tickets"));

        return ResponseEntity.ok(recurso);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar tickets por Cliente", description = "Retorna todos los tickets que ha comprado un cliente especifico")
    public ResponseEntity<CollectionModel<EntityModel<TicketResponse>>> buscarPorClienteId(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Integer clienteId) {
        
        log.info("Peticion REST recibida para buscar tickets del cliente: {}", clienteId);
        List<TicketResponse> lista = ticketService.listarPorCliente(clienteId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // HATEOAS para listas de tickets por cliente
        List<EntityModel<TicketResponse>> ticketsRecursos = lista.stream()
                .map(ticket -> EntityModel.of(ticket,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).buscarTicket(ticket.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TicketResponse>> coleccion = CollectionModel.of(ticketsRecursos);
        coleccion.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).buscarPorClienteId(clienteId)).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }
}