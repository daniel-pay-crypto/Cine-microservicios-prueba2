package com.cine.ms_director.controller;

import com.cine.ms_director.dto.DirectorRequest;
import com.cine.ms_director.dto.DirectorResponse;
import com.cine.ms_director.model.Director;
import com.cine.ms_director.service.DirectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/directores")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService service;

    @GetMapping
    public ResponseEntity<List<Director>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponse> obtenerPorId(@PathVariable Long id) {
        // El controlador solo llama al service y devuelve la respuesta
        return ResponseEntity.ok(service.obtenerDirectorConPeliculas(id));
    }

    @PostMapping
    public ResponseEntity<Director> guardar(@Valid @RequestBody DirectorRequest request) {
        Director d = new Director();
        d.setNombres(request.getNombres());
        d.setApellidos(request.getApellidos());
        d.setNacionalidad(request.getNacionalidad());
        return new ResponseEntity<>(service.guardar(d), HttpStatus.CREATED);
    }
}