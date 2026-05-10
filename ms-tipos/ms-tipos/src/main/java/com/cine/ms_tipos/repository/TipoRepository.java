package com.cine.ms_tipos.repository;

import java.util.Optional;
import com.cine.ms_tipos.model.TipoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends JpaRepository<TipoModel, Integer> {

    Optional<TipoModel> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}