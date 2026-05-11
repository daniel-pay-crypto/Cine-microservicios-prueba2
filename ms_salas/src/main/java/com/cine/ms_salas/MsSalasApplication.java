package com.cine.ms_salas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients 
public class MsSalasApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsSalasApplication.class, args);
    }
}