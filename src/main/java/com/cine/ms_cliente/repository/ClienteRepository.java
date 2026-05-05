package com.cine.ms_cliente.repository;

import java.util.Optional;
import com.cine.ms_cliente.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface ClienteRepository extends JpaRepository<ClienteModel, Integer> {

   // Spring Data JPA generará el SQL automáticamente basándose en los nombres de estos métodos!

    // Busca un cliente por su RUN
    Optional<ClienteModel> findByRun(String run);

    // Busca un cliente por su correo
    Optional<ClienteModel> findByCorreo(String correo);

    // Devuelve true si ya existe un cliente con ese RUN (muy útil para validaciones antes de guardar)
    boolean existsByRun(String run);

    // Devuelve true si ya existe un cliente con ese correo
    boolean existsByCorreo(String correo); 

}
