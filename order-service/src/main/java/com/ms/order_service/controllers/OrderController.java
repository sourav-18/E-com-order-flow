package com.ms.order_service.controllers;

import com.ms.order_service.dtos.ApiResponseDto;
import com.ms.order_service.dtos.OrderCreateRequestDto;
import com.ms.order_service.dtos.OrderCreateResponseDto;
import com.ms.order_service.dtos.OrderDto;
import com.ms.order_service.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<OrderCreateResponseDto>> create(HttpServletRequest request, @Valid @RequestBody OrderCreateRequestDto body){
        Long userId = Long.valueOf(request.getHeader("user-id"));
        OrderCreateResponseDto orderCreateResponseDto = orderService.create(body,userId);
        ApiResponseDto<OrderCreateResponseDto> apiResponse=new ApiResponseDto<>
                (orderCreateResponseDto.getStatus(),"Order create successfully",orderCreateResponseDto);
       return ResponseEntity.status(orderCreateResponseDto.getStatus()).body(apiResponse);
    }

    @GetMapping("/{id}")
    public OrderDto details(@PathVariable("id") Long id){
       return orderService.details(id);
    }
}
