package com.cine.ms_pelicula.service;

import com.cine.ms_pelicula.exception.ResourceNotFoundException;
import com.cine.ms_pelicula.model.Pelicula;
import com.cine.ms_pelicula.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repository;

    public List<Pelicula> listarTodas() {
        return repository.findAll();
    }

    public Pelicula buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la película con ID: " + id));
    }

    public Pelicula guardar(Pelicula pelicula) {
        return repository.save(pelicula);
    }

    public List<Pelicula> buscarPorGenero(Long generoId) {
        return repository.findByGeneroId(generoId);
    }

    public List<Pelicula> buscarPorDirector(Long directorId) {
        return repository.findByDirectorId(directorId);
    }
}