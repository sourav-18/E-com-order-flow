package com.ms.order_service.controllers;

import com.ms.order_service.clients.InventoryClient;
import com.ms.order_service.dtos.*;
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
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventoryClient inventoryClient;

    private ProductDto getProductDot() {
        ProductDto product = new ProductDto();
        product.setId(10L);
        product.setPrice(12);
        product.setAvailableQuantity(20);
        return product;
    }

    private OrderCreateRequestDto getOrderDto(Long productId) {
        OrderCreateRequestDto order = new OrderCreateRequestDto();
        order.setProductId(productId);
        order.setQuantity(2);
        order.setIdempotencyKey("test-key-123");
        return order;
    }

    private OrderCreateResponseDto createOrder(ProductDto product, OrderCreateRequestDto order) throws Exception {
        Mockito.when(inventoryClient.getProductDetails(10L))
                .thenReturn(product);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/orders")
                                .header("user-id", "100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(order))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        ApiResponseDto<OrderCreateResponseDto> response =
                objectMapper.readValue(
                        result.getResponse().getContentAsString(),
                        new TypeReference<ApiResponseDto<OrderCreateResponseDto>>() {
                        }
                );

        return response.getData();
    }

    @Test
    void shouldCreateOrder() throws Exception {
        ProductDto product = getProductDot();
        OrderCreateRequestDto orderCreateDto = getOrderDto(product.getId());
        createOrder(product, orderCreateDto);
    }

    @Test
    void idempotencyCheck() throws Exception {
        ProductDto product = getProductDot();
        OrderCreateRequestDto orderCreateDto = getOrderDto(product.getId());
        OrderCreateResponseDto orderResponse = createOrder(product, orderCreateDto);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/orders")
                                .header("user-id", "100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(orderCreateDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.data.id").value(orderResponse.getId()));
    }

    @Test
    void insufficientStockCheck() throws Exception {
        ProductDto product = getProductDot();
        OrderCreateRequestDto orderCreateDto = getOrderDto(product.getId());
        orderCreateDto.setQuantity(product.getAvailableQuantity()+1);

        Mockito.when(inventoryClient.getProductDetails(10L))
                .thenReturn(product);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/orders")
                                .header("user-id", "100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(orderCreateDto))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}


