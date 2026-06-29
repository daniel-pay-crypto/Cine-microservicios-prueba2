package com.cine.ms_gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class GatewayLogginFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        

        System.out.println(">>> Petición interceptada en el Gateway hacia: " + exchange.getRequest().getURI());


//Aca le digo al Gateway que deje pasar la petición solamente al microservicio correspondiente
        return chain.filter(exchange);
    }


}

