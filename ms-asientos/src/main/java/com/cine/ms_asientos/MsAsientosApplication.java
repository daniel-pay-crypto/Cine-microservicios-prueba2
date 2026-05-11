package com.cine.ms_asientos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // <- Importación

@SpringBootApplication
@EnableFeignClients
public class MsAsientosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAsientosApplication.class, args);
    }
}