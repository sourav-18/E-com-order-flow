package com.ms.inventory_service.filters;

import com.ms.inventory_service.dtos.ApiErrorResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AllowIpFilter extends OncePerRequestFilter {
    private final DiscoveryClient discoveryClient;
    private final ObjectMapper objectMapper;

    @Value("${gateway.service.name}")
    private String gatewayServiceName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isValidClient = discoveryClient.getInstances(gatewayServiceName).stream()
                .anyMatch((item) ->
                        item.getHost().equals(request.getRemoteHost())
                );
        if (isValidClient) {
            filterChain.doFilter(request, response);
        } else {
            ApiErrorResponseDto errorResponse =
                    new ApiErrorResponseDto(
                            HttpStatus.FORBIDDEN.value(),
                            "Client IP not allowed"
                    );

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    objectMapper.writeValueAsString(errorResponse)
            );
        }
    }
}
