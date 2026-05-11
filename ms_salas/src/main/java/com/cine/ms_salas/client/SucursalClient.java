package com.cine.ms_salas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-sucursales", url = "http://localhost:8085/api/sucursales")
public interface SucursalClient {

    @GetMapping("/{id}")
    Object obtenerPorId(@PathVariable("id") Long id);
}