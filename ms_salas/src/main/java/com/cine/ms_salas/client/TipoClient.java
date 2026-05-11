package com.cine.ms_salas.client;

import com.cine.ms_salas.dto.TipoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-tipos", url = "http://localhost:8089/api/v1/tipos")
public interface TipoClient {

    @GetMapping("/{id}")
    TipoDTO obtenerPorId(@PathVariable("id") Integer id);
}