package com.cine.ms_cliente.controller;

import com.cine.ms_cliente.dto.ClienteDetalleDTO;
import com.cine.ms_cliente.model.Cliente;
import com.cine.ms_cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        log.info("Endpoint POST /api/v1/clientes consumido");
        Cliente creado = service.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<ClienteDetalleDTO> obtenerDetalle(@PathVariable Long id) {
        log.info("Endpoint GET /api/v1/clientes/{}/tickets consumido", id);
        return ResponseEntity.ok(service.obtenerClienteConTickets(id));
    }
}