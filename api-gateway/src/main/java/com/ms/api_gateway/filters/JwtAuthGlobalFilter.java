package com.ms.api_gateway.filters;

import com.ms.api_gateway.services.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

@Component
@RequiredArgsConstructor
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String uriPath = exchange.getRequest().getURI().getPath();

        if (uriPath.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
        }

        if (token != null) {
            try {
                if (!jwtService.isTokenExpired(token)) {
                    Claims claims = jwtService.verifySignatureAdnExtractAllClaims(token);
                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(builder -> builder.header("user-id", claims.getId()))
                            .build();
                    return chain.filter(mutatedExchange).then(
                            Mono.fromRunnable(() -> {
                                // post filter logic here
                            })
                    );
                }
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();

    }

    @Override
    public int getOrder() {
        return -1;
    }
}


