package com.cine.ms_pelicula.controller;

import com.cine.ms_pelicula.dto.PeliculaFullResponse;
import com.cine.ms_pelicula.dto.PeliculaRequest;
import com.cine.ms_pelicula.model.Pelicula;
import com.cine.ms_pelicula.service.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/peliculas")
@RequiredArgsConstructor
public class PeliculaController {

    private final PeliculaService service;

    @GetMapping
    public ResponseEntity<List<Pelicula>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/director/{directorId}")
    public ResponseEntity<List<Pelicula>> listarPorDirector(@PathVariable Long directorId) {
        return ResponseEntity.ok(service.buscarPorDirector(directorId));
    }

    @PostMapping
    public ResponseEntity<Pelicula> guardar(@Valid @RequestBody PeliculaRequest request) {
        Pelicula p = new Pelicula();
        p.setTitulo(request.getTitulo());
        p.setDuracionMinutos(request.getDuracionMinutos());
        p.setGeneroId(request.getGeneroId());
        p.setDirectorId(request.getDirectorId());
        return new ResponseEntity<>(service.guardar(p), HttpStatus.CREATED);
    }
    // Llamadas lógicas a los otros microservicios (Lado "Muchos")
    @GetMapping("/{id}/detalles")
    public ResponseEntity<PeliculaFullResponse> obtenerDetalleCompleto(@PathVariable Long id) {
    PeliculaFullResponse detalle = service.obtenerDetalleCompleto(id);
    return ResponseEntity.ok(detalle);

    
    }

}