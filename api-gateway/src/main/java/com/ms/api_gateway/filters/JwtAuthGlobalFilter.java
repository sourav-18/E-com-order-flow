package com.ms.api_gateway.filters;

import com.ms.api_gateway.dots.ApiErrorResponseDto;
import com.ms.api_gateway.services.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper=new ObjectMapper();

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

                    ServerHttpRequest build = exchange.getRequest()
                            .mutate()
                            .header("user-id", claims.getSubject())
                            .build();

                    return chain.filter(
                            exchange.mutate()
                                    .request(build)
                                    .build()
                    );
                }
            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                return exchange.getResponse().setComplete();
            }
        }
        ApiErrorResponseDto error =
                new ApiErrorResponseDto(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Token is not provided or Token is not valid"
                );

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse()
                .getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes = objectMapper.writeValueAsBytes(error);

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(bytes);

        return exchange.getResponse()
                .writeWith(Mono.just(buffer));

    }

    @Override
    public int getOrder() {
        return -1;
    }
}


