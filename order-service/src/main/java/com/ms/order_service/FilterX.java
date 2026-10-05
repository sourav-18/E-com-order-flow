package com.ms.order_service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilterX extends OncePerRequestFilter {
    private final DiscoveryClient discoveryClient;
    @Value("${service.allow}")
    private String allowServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String[] split = allowServices.split(",");
        for (String service : split) {
            boolean b = discoveryClient.getInstances(service).stream()
                    .anyMatch((item) -> item.getHost().equals(request.getRemoteHost()));
        }
//        for (ServiceInstance item : instances) {
//            System.out.println(item.getHost());
//        }
        System.out.println(allowServices);
        filterChain.doFilter(request, response);
    }
}
