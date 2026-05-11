package com.cine.ms_salas_plural.repository;

import com.cine.ms_salas_plural.model.Salas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SalasRepository extends JpaRepository<Salas, Long> {
    
    Optional<Salas> findBySalaId(Long salaId); 

    public SalasDTO obtenerPorSalaId(Long salaId) {
        Salas sala = salasRepository.findBySalaId(salaId)
                .orElseThrow(() -> new RuntimeException("Sala con ID " + salaId + " no encontrada"));
        return mapToDTO(sala);
    }

}