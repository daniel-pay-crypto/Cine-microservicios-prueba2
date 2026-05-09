package com.cine.ms_pelicula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@FeignClient // Configuración de Feign Client para comunicarse con ms-cliente
public class MsPeliculaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPeliculaApplication.class, args);
	}

}
