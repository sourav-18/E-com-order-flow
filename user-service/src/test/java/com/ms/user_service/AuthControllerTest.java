package com.ms.user_service;

import com.ms.user_service.dtos.SignupRequestDto;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signupTest() throws Exception {

        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setName("sourav");
        requestDto.setEmail("sourav@gmail.com");
        requestDto.setPassword("password");

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/auth/signup")
                                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isCreated());
    }
}

