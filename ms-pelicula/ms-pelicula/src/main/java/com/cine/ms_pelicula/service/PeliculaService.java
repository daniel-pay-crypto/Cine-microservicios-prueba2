package com.cine.ms_pelicula.service;

import com.cine.ms_pelicula.dto.GeneroDTO;
import com.cine.ms_pelicula.dto.PeliculaFullResponse;
import com.cine.ms_pelicula.dto.SalaDTO;
import com.cine.ms_pelicula.dto.TicketDTO;
import com.cine.ms_pelicula.exception.ResourceNotFoundException;
import com.cine.ms_pelicula.model.Pelicula;
import com.cine.ms_pelicula.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@Slf4j
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository repository;
    private final GeneroFeign generoFeign;
    private final TicketFeign ticketFeign;
    private final SalaFeign salaFeign;

    @Transactional(readOnly = true)
    public List<Pelicula> listarTodas() {
        log.info("Obteniendo todas las películas");
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Pelicula buscarPorId(Long id) {
        log.info("Buscando película con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con el ID: " + id));
    }

    @Transactional
    public Pelicula guardar(Pelicula pelicula) {
        log.info("Registrando nueva película: {}", pelicula.getTitulo());
        return repository.save(pelicula);
    }

    @Transactional(readOnly = true)
    public List<Pelicula> buscarPorDirector(Long directorId) {
        log.info("Buscando películas del director ID: {}", directorId);
        return repository.findByDirectorId(directorId);
    }

    @Transactional(readOnly = true)
    public PeliculaFullResponse obtenerDetalleCompleto(Long id) {
        log.info("Obteniendo detalle completo de la película ID: {}", id);
        
        Pelicula pelicula = this.buscarPorId(id);

        List<GeneroDTO> generos = generoFeign.getGenerosByPeliculaId(id);
        List<TicketDTO> tickets = ticketFeign.getTicketsByPeliculaId(id);
        List<SalaDTO> salas = salaFeign.getSalasByPeliculaId(id);

        PeliculaFullResponse response = new PeliculaFullResponse();
        response.setId(pelicula.getId());
        response.setTitulo(pelicula.getTitulo());
        response.setGeneros(generos);
        response.setTickets(tickets);
        response.setSalas(salas);

        return response;
    }
}

// Interfaces Feign
@FeignClient(name = "ms-genero", url = "http://localhost:8090/api/v1/generos")
interface GeneroFeign {
    @GetMapping("/pelicula/{peliculaId}")
    List<GeneroDTO> getGenerosByPeliculaId(@PathVariable("peliculaId") Long peliculaId);
}

@FeignClient(name = "ms-tickets", url = "http://localhost:8089/api/v1/tickets")
interface TicketFeign {
    @GetMapping("/pelicula/{peliculaId}")
    List<TicketDTO> getTicketsByPeliculaId(@PathVariable("peliculaId") Long peliculaId);
}

@FeignClient(name = "ms-salas", url = "http://localhost:8086/api/v1/salas")
interface SalaFeign {
    @GetMapping("/pelicula/{peliculaId}")
    List<SalaDTO> getSalasByPeliculaId(@PathVariable("peliculaId") Long peliculaId);
}