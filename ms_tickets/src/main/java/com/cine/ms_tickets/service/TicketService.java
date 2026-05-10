package com.cine.ms_tickets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.ms_tickets.model.Ticket;
import com.cine.ms_tickets.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // 1. Obtener todos los tickets
    public List<Ticket> obtenerTodosLosTickets() {
        return ticketRepository.findAll();
    }

    // 2. Obtener un ticket específico por su ID
    public Optional<Ticket> obtenerTicketPorId(Integer id) {
        return ticketRepository.findById(id);
    }

    // 3. Crear o actualizar un ticket (Con regla de negocio)
    public Ticket guardarTicket(Ticket ticket) {
        if (ticket.getId() == null && ticket.getAsientoId() != null) {
            boolean asientoOcupado = ticketRepository.existsByAsientoId(ticket.getAsientoId());
            if (asientoOcupado) {
                throw new RuntimeException("Error: El asiento con ID " + ticket.getAsientoId() + " ya ha sido vendido.");
            }
        }
        return ticketRepository.save(ticket);
    }

    // 4. Buscar tickets por ID de cliente
    public List<Ticket> obtenerTicketsPorCliente(Integer clienteId) {
        return ticketRepository.findByClienteId(clienteId);
    }

    // 5. Buscar tickets por ID de película
    public List<Ticket> obtenerTicketsPorPelicula(Integer peliculaId) {
        return ticketRepository.findByPeliculaId(peliculaId);
    }

    // 6. Buscar tickets por el nombre del puesto (ej. "A12")
    public List<Ticket> obtenerTicketsPorPuesto(String puesto) {
        return ticketRepository.findByPuesto(puesto);
    }

    // 7. Eliminar un ticket
    public void eliminarTicket(Integer id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
        } else {
            throw new RuntimeException("Error: No se puede eliminar el ticket porque el ID " + id + " no existe.");
        }
    }
}