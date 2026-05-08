package com.cine.ms_ubicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cine.ms_salas.MsSalaApplication;

@SpringBootApplication
public class MsUbicacionApplication {
    public static void main(String[] args){
        System.setProperty("spring.profiles.active", "ubicacion");
        SpringApplication.run(MsSalaApplication.class, args);
    }
}