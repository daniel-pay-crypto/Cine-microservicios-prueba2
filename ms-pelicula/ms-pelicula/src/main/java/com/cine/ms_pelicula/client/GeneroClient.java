package com.cine.ms_pelicula.client;

import com.cine.ms_pelicula.dto.GeneroDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-genero", url = "http://localhost:8091/api/v1/generos")
public interface GeneroClient {
    
    @GetMapping("/{id}")
    GeneroDTO buscarPorId(@PathVariable("id") Long id);
}