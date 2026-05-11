package com.cine.ms_tickets.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente", url = "http://localhost:8083/api/v1/clientes")
public interface ClienteClient {
    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Integer id);
}