package com.cine.ms_salas.repository;

import com.cine.ms_salas.model.Salas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalasRepository extends JpaRepository<Salas, Long> {
   
}