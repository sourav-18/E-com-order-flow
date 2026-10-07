package com.ms.order_service.exceptions;

import com.ms.order_service.dtos.ApiErrorResponseDto;
import com.ms.order_service.dtos.ApiResponseDto;
import com.ms.order_service.dtos.S2SErrorResponseDto;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;


@Component
@RequiredArgsConstructor
public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        S2SErrorResponseDto responseBody = extractResponseBody(response);
        String message = responseBody.getMessage()!=null?responseBody.getMessage():response.reason();
        return new FeignClientException(message, responseBody.getStatus());
    }

    private S2SErrorResponseDto extractResponseBody(Response response) {
        try {
            String s=new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return objectMapper.readValue(s, S2SErrorResponseDto.class);
        } catch (Exception ex) {
            return new S2SErrorResponseDto(500, null);
        }
    }
}
