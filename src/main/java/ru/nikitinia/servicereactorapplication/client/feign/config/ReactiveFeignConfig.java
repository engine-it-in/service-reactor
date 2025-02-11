package ru.nikitinia.servicereactorapplication.client.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;


@Configurable
@RequiredArgsConstructor
public class ReactiveFeignConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ReactiveFeignErrorDecoder(objectMapper);
    }

}