package ru.nikitinia.servicereactorapplication.client.feign.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceClientException;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceServerException;
import ru.nikitinia.servicereactorapplication.model.error.ErrorExternalServiceModel;

import java.io.IOException;
import java.nio.charset.Charset;

@RequiredArgsConstructor
public class ReactiveFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        String body;

        if (400 <= response.status() && response.status() <= 500) {
            try {
                body = IOUtils.toString(response.body().asInputStream(), Charset.defaultCharset());
                ErrorExternalServiceModel errorExternalServiceModel = objectMapper.readValue(body, ErrorExternalServiceModel.class);
                return new ExternalServiceClientException(errorExternalServiceModel.detail());
            } catch (IOException exception) {
                return new ExternalServiceClientException(exception.getMessage());
            }
        }
        return new ExternalServiceServerException("Ошибка при вызове " + methodKey);
    }
}
