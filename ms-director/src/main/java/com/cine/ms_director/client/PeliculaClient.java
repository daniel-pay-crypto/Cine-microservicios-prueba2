package com.cine.ms_director.client;

import com.cine.ms_director.dto.PeliculaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "ms-pelicula", url = "http://localhost:8082/api/v1/peliculas")
public interface PeliculaClient {

    @GetMapping("/director/{directorId}")
    List<PeliculaDTO> getPeliculasByDirectorId(@PathVariable("directorId") Long directorId);
}