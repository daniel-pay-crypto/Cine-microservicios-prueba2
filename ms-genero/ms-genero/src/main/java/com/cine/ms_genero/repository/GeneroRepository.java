package com.cine.ms_genero.repository;

import java.util.Optional;
import com.cine.ms_genero.model.GeneroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<GeneroModel, Integer> {

    Optional<GeneroModel> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}