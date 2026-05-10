package com.cine.ms_pelicula.repository;

import java.util.List;
import com.cine.ms_pelicula.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    
    List<Pelicula> findByGeneroId(Long generoId);
  
    List<Pelicula> findByDirectorId(Long directorId);
}