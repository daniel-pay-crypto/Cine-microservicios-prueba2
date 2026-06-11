package com.cine.ms_pelicula.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-director", url = "http://localhost:8083/api/v1/directores")
public interface DirectorClient {

    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Long id); 
}