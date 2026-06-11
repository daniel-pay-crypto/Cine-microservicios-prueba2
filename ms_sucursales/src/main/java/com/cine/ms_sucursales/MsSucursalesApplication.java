package com.cine.ms_sucursales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients 
public class MsSucursalesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsSucursalesApplication.class, args);
    }
}