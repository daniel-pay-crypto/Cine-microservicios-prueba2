package com.cine.ms_asientos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cine.ms_asientos.model.Asiento;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {

}