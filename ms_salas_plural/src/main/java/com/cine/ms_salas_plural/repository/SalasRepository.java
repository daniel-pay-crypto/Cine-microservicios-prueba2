package com.cine.ms_salas_plural.repository;

import com.cine.ms_salas_plural.model.Salas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalasRepository extends JpaRepository<Salas, Long> {
    
  List<Salas> findBySalaId(Long salaId); 

}