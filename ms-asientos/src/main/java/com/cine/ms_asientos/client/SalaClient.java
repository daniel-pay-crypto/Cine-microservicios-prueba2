package com.cine.ms_asientos.client;

import com.cine.ms_asientos.dto.SalaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-salas", url = "http://localhost:8088/api/v1/salas")
public interface SalaClient {

    @GetMapping("/{salaId}")
    SalaDTO obtenerPorSalaId(@PathVariable("salaId") Long salaId);
}