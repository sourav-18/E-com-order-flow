package com.ms.payment_service.controllers;

import com.ms.payment_service.clients.InventoryClient;
import com.ms.payment_service.dtos.ApiResponseDto;
import com.ms.payment_service.dtos.PaymentResponseDto;
import com.ms.payment_service.dtos.ProductReserveDto;
import com.ms.payment_service.dtos.ProductReservesStatusType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    InventoryClient inventoryClient;

    private ProductReserveDto getProductReserveDto(Long productId, Long orderId, Long userId) {
        return ProductReserveDto.builder()
                .id(1L)
                .orderId(orderId)
                .userId(userId)
                .productId(productId)
                .quantity(10)
                .status(ProductReservesStatusType.pending)
                .totalAmount(100)
                .build();
    }

    private PaymentResponseDto createPayment(ProductReserveDto productReserveDto) throws Exception {

        Mockito.when(inventoryClient.reservedDetails(productReserveDto.getOrderId()))
                .thenReturn(productReserveDto);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/payments/"+productReserveDto.getOrderId())
                                .header("user-id", productReserveDto.getUserId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productReserveDto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        ApiResponseDto<PaymentResponseDto> response =
                objectMapper.readValue(
                        result.getResponse().getContentAsString(),
                        new TypeReference<ApiResponseDto<PaymentResponseDto>>() {
                        }
                );

        return response.getData();
    }

    @Test
    public void shouldCreatePayment() throws Exception {
        Long userId=1L;
        Long orderId=1L;
        Long productId=1L;
        ProductReserveDto productReserveDto = getProductReserveDto(productId,orderId,userId);
        Mockito.when(inventoryClient.reservedDetails(10L))
                .thenReturn(productReserveDto);
        createPayment(productReserveDto);
    }


}
