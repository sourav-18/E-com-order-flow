package com.ms.inventory_service.controllers;

import com.ms.inventory_service.entities.ProductEntity;
import com.ms.inventory_service.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    private ProductEntity createProduct() {
        ProductEntity entity = ProductEntity.builder()
                .name("product")
                .availableQuantity(10)
                .price(10)
                .build();
        return productRepository.save(entity);
    }

    @Test
    public void shouldGetProductDetails() throws Exception {
        ProductEntity product = createProduct();
         mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/products/"+product.getId())

                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()));
    }


}
