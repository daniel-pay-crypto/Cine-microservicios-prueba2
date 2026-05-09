package com.cine.ms_pelicula.controller;

import com.cine.ms_pelicula.dto.PeliculaRequest;
import com.cine.ms_pelicula.dto.PeliculaResponse;
import com.cine.ms_pelicula.model.Pelicula;
import com.cine.ms_pelicula.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService service;

    //
    @GetMapping
    public ResponseEntity<List<PeliculaResponse>> listarTodas() {
        List<PeliculaResponse> respuestas = service.listarTodas().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaResponse> obtenerPorId(@PathVariable Long id) {
        Pelicula pelicula = service.buscarPorId(id);
        return ResponseEntity.ok(mapearAResponse(pelicula));
    }
//Take me back to the night we met...
    @PostMapping
    public ResponseEntity<PeliculaResponse> guardar(@RequestBody PeliculaRequest request) {
        Pelicula nuevaPelicula = new Pelicula();
        nuevaPelicula.setTitulo(request.getTitulo());
        nuevaPelicula.setSinopsis(request.getSinopsis());
        nuevaPelicula.setDuracionMinutos(request.getDuracionMinutos());
        nuevaPelicula.setGeneroId(request.getGeneroId());
        nuevaPelicula.setDirectorId(request.getDirectorId());

        Pelicula guardada = service.guardar(nuevaPelicula);
        return new ResponseEntity<>(mapearAResponse(guardada), HttpStatus.CREATED);
    }

    // rutas feign para Ms-Genero y Ms-Director:

    @GetMapping("/genero/{generoId}")
    public ResponseEntity<List<PeliculaResponse>> listarPorGenero(@PathVariable Long generoId) {
        List<PeliculaResponse> respuestas = service.buscarPorGenero(generoId).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuestas);
    }
//Because...
    @GetMapping("/director/{directorId}")
    public ResponseEntity<List<PeliculaResponse>> listarPorDirector(@PathVariable Long directorId) {
        List<PeliculaResponse> respuestas = service.buscarPorDirector(directorId).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuestas);
    }
//...Because I still have some things to say to her...

    // Método auxiliar para convertir Modelo a DTO :D
    private PeliculaResponse mapearAResponse(Pelicula pelicula) {
        PeliculaResponse response = new PeliculaResponse();
        response.setId(pelicula.getId());
        response.setTitulo(pelicula.getTitulo());
        response.setSinopsis(pelicula.getSinopsis());
        response.setDuracionMinutos(pelicula.getDuracionMinutos());
        response.setGeneroId(pelicula.getGeneroId());
        response.setDirectorId(pelicula.getDirectorId());
        return response;
    }
}