package com.cine.ms_sucursales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cine.ms_salas.MsSalaApplication;

@SpringBootApplication
public class MsSucursalesApplication {

	public static void main(String[] args){
        System.setProperty("spring.profiles.active", "sucursales");
        SpringApplication.run(MsSalaApplication.class, args);
    }

}
