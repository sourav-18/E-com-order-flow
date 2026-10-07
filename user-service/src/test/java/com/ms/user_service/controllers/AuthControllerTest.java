package com.ms.user_service.controllers;

import com.ms.user_service.dtos.LoginRequestDto;
import com.ms.user_service.dtos.SignupRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SignupRequestDto getSignupRequestDto() {
        SignupRequestDto request = new SignupRequestDto();
        request.setName("Sourav");
        request.setEmail("sourav@gmail.com");
        request.setPassword("password123");
        return request;
    }

    private void signup(SignupRequestDto request) throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("x-access-token"));
    }

    @Test
    void shouldSignupUser() throws Exception {
        SignupRequestDto request = getSignupRequestDto();
        signup(request);
    }

    @Test
    void shouldRejectDuplicateEmail() throws Exception {

        SignupRequestDto request = getSignupRequestDto();

        signup(request);

        // Second signup - same email
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldLogin() throws Exception {
        SignupRequestDto signupRequestDto = getSignupRequestDto();
        signup(signupRequestDto); //signup

        LoginRequestDto loginRequestDto=new LoginRequestDto();
        loginRequestDto.setEmail(signupRequestDto.getEmail());
        loginRequestDto.setPassword(signupRequestDto.getPassword());


        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequestDto))
                )
                .andExpect(status().isOk())
                .andExpect(header().exists("x-access-token"));
    }




}
