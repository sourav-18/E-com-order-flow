package com.ms.payment_service.controllers;

import com.ms.payment_service.dtos.ApiResponseDto;
import com.ms.payment_service.dtos.PaymentResponseDto;
import com.ms.payment_service.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseEntity<ApiResponseDto<PaymentResponseDto>> payment(@PathVariable Long orderId){
        PaymentResponseDto payment = paymentService.payment(orderId);
        ApiResponseDto<PaymentResponseDto> apiResponse=new ApiResponseDto<>(payment.getResponseCode(),"Payment initiate successfully",payment);
        return ResponseEntity.status(payment.getResponseCode()).body(apiResponse);
    }
}
