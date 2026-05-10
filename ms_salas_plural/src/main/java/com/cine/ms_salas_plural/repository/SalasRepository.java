package com.cine.ms_salas_plural.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cine.ms_salas_plural.model.Salas;

@Repository
public interface SalasRepository extends JpaRepository<Salas, Long> {
   
}