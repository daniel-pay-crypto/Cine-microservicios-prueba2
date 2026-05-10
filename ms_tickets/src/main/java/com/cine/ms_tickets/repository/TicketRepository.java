package com.cine.ms_tickets.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.ms_tickets.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Busca todos los tickets que ha comprado un cliente específico
    List<Ticket> findByClienteId(Integer clienteId);

    // Busca todos los tickets vendidos para una película específica
    List<Ticket> findByPeliculaId(Integer peliculaId);

    // Devuelve true si un asiento en particular ya fue vendido/asignado a un ticket
    boolean existsByAsientoId(Integer asientoId);

    // Busca tickets por el nombre del puesto (ej: "A12")
    List<Ticket> findByPuesto(String puesto);
}