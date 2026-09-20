package com.ms.order_service.controllers;

import com.ms.order_service.dtos.OrderCreateRequestDto;
import com.ms.order_service.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Object create(@RequestBody OrderCreateRequestDto body){
        return orderService.create(body);
    }
}
