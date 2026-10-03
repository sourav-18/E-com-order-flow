package com.ms.api_gateway.filters;

import com.ms.api_gateway.services.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtGatewayFilterFactory
        extends AbstractGatewayFilterFactory<JwtGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public JwtGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String authHeader =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return chain.filter(exchange);
            }

            String token = authHeader.substring(7);

            if (token.isBlank()) {
                return chain.filter(exchange);
            }

            try {
                if (jwtService.isTokenExpired(token)) {
                    return unauthorized(exchange);
                }

                Claims claims =
                        jwtService.verifySignatureAdnExtractAllClaims(token);

                String role = claims.get("Role", String.class);


                return chain.filter(
                        exchange.mutate()
                                .request(
                                        exchange.getRequest()
                                                .mutate()
                                                .build()
                                )
                                .build()
                );

            } catch (Exception e) {
                return unauthorized(exchange);
            }
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}


