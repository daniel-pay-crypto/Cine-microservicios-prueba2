package com.cine.ms_director.service;

import com.cine.ms_director.dto.DirectorResponse;
import com.cine.ms_director.dto.PeliculaDTO;
import com.cine.ms_director.exception.ResourceNotFoundException;
import com.cine.ms_director.model.Director;
import com.cine.ms_director.repository.DirectorRepository;
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
public class DirectorService {

    private final DirectorRepository repository;
    
    private final PeliculaFeign peliculaFeign;

    @Transactional(readOnly = true)
    public List<Director> listarTodos() {
        log.info("Obteniendo lista de directores");
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Director buscarPorId(Long id) {
        log.info("Buscando director ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado con ID: " + id));
    }

    @Transactional
    public Director guardar(Director director) {
        log.info("Guardando director: {} {}", director.getNombres(), director.getApellidos());
        return repository.save(director);
    }

    // MÉTODO DE RELACIÓN: Obtiene el director y le pide sus películas al otro microservicio
    @Transactional(readOnly = true)
    public DirectorResponse obtenerDirectorConPeliculas(Long id) {
        log.info("Buscando director y sus películas para el ID: {}", id);
        
        Director director = this.buscarPorId(id);
        
        // Llamada al microservicio de películas (La pata de gallo está allá)
        List<PeliculaDTO> peliculas = peliculaFeign.getPeliculasByDirectorId(id);

        DirectorResponse response = new DirectorResponse();
        response.setId(director.getId());
        response.setNombres(director.getNombres());
        response.setApellidos(director.getApellidos());
        response.setNacionalidad(director.getNacionalidad());
        response.setPeliculas(peliculas);

        return response;
    }
}


@FeignClient(name = "ms-pelicula", url = "http://localhost:8082/api/peliculas")
interface PeliculaFeign {
    @GetMapping("/director/{directorId}")
    List<PeliculaDTO> getPeliculasByDirectorId(@PathVariable("directorId") Long directorId);
}