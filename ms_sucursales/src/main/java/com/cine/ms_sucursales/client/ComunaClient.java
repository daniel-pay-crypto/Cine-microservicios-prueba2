package com.cine.ms_sucursales.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-ubicacion", url = "http://localhost:8086/api/v1/comunas")
public interface ComunaClient {

    @GetMapping("/{id}")
    Object obtenerPorId(@PathVariable("id") Long id);
}