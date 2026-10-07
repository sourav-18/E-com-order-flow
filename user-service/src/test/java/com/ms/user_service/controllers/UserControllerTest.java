package com.ms.user_service.controllers;

import com.ms.user_service.dtos.ApiResponseDto;
import com.ms.user_service.dtos.SignupRequestDto;
import com.ms.user_service.dtos.SignupResponseDtoTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SignupRequestDto getSignupRequestDto() {
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setName("Sourav");
        signupRequestDto.setEmail("sourav@gmail.com");
        signupRequestDto.setPassword("password123");
        return signupRequestDto;
    }

    private SignupResponseDtoTest signup(SignupRequestDto signupRequestDto) throws Exception {

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signupRequestDto))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("x-access-token"))
                .andReturn();

        ApiResponseDto<SignupResponseDtoTest> response =
                objectMapper.readValue(
                        result.getResponse().getContentAsString(),
                        new TypeReference<ApiResponseDto<SignupResponseDtoTest>>() {
                        }
                );
        String token = result.getResponse().getHeader("x-access-token");
        response.getData().setToken(token);
        return response.getData();
    }

    @Test
    void shouldGetProfileDetails() throws Exception {

        SignupRequestDto request = getSignupRequestDto();

        // Signup and get token
        SignupResponseDtoTest signupResponse = signup(request);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/users/profile")
                                .header("Authorization", "Bearer " + signupResponse.getToken())
                                .header("user-id", signupResponse.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(signupResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(request.getName()))
                .andExpect(jsonPath("$.data.email").value(request.getEmail()));
    }
}
