package com.cine.ms_director;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsDirectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsDirectorApplication.class, args);
    }
}