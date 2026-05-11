package com.cine.ms_salas_plural.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-salas", url = "http://localhost:8087/api/v1/sala")
public interface SalaClient {

    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}