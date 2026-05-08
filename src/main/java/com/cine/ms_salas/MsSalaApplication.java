package com.cine.ms_salas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsSalaApplication { 
    public static void main(String[] args){
        System.setProperty("spring.profiles.active", "salas");
        SpringApplication.run(MsSalaApplication.class, args);
        }
    
}