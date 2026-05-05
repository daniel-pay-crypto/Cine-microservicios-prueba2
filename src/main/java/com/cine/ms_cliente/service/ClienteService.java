package com.cine.ms_cliente.service;

import com.cine.ms_cliente.dto.ClienteRequest;
import com.cine.ms_cliente.dto.ClienteResponse;
import com.cine.ms_cliente.model.ClienteModel;
import com.cine.ms_cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Invocamos a SLF4J para dejar logs estructurados[cite: 8].
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // 1. Guardar un Cliente (Convierte el DTO a Entidad y guarda)
    public ClienteResponse guardarCliente(ClienteRequest request) {
        log.info("Iniciando guardado de nuevo cliente con RUN: {}", request.getRun());

        // Validaciones manuales extra (opcional, pero buena práctica)
        if (clienteRepository.existsByRun(request.getRun())) {
            log.error("Error al guardar: Ya existe un cliente con el RUN {}", request.getRun());
            throw new RuntimeException("Ya existe un cliente con ese RUN");
        }
        if (clienteRepository.existsByCorreo(request.getCorreo())) {
            log.error("Error al guardar: Ya existe un cliente con el correo {}", request.getCorreo());
            throw new RuntimeException("Ya existe un cliente con ese correo");
        }

        // Convertimos el Request DTO a Entidad JPA
        ClienteModel cliente = new ClienteModel();
        cliente.setRun(request.getRun());
        cliente.setNombres(request.getNombres());
        cliente.setApellidos(request.getApellidos());
        cliente.setCorreo(request.getCorreo());
        cliente.setFechaNacimiento(request.getFechaNacimiento());

        ClienteModel clienteGuardado = clienteRepository.save(cliente);
        log.info("Cliente guardado exitosamente con ID: {}", clienteGuardado.getId());

        return mapToResponse(clienteGuardado);
    }

    // 2. Listar todos los clientes
    public List<ClienteResponse> listarTodos() {
        log.info("Buscando todos los clientes");
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 3. Buscar por ID
    public ClienteResponse buscarPorId(Integer id) {
        log.info("Buscando cliente con ID: {}", id);
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cliente no encontrado con ID: {}", id);
                    return new RuntimeException("Cliente no encontrado con ID: " + id);
                });
        return mapToResponse(cliente);
    }

    // Método utilitario para convertir Entidad a Response DTO
    private ClienteResponse mapToResponse(ClienteModel cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .run(cliente.getRun())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .correo(cliente.getCorreo())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .build();
    }
}