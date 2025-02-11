package ru.nikitinia.servicereactorapplication.client.feign.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceClientException;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceServerException;
import ru.nikitinia.servicereactorapplication.model.error.ErrorExternalServiceModel;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;
import static ru.nikitinia.servicereactorapplication.util.TestDataBuilder.getTestFeignResponse;

class ReactiveFeignErrorDecoderTest {

    private ReactiveFeignErrorDecoder reactiveFeignErrorDecoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        reactiveFeignErrorDecoder = new ReactiveFeignErrorDecoder(objectMapper);
    }

    @Test
    void checkReactiveFeignErrorDecoder() {
        assertThat(reactiveFeignErrorDecoder)
                .hasFieldOrPropertyWithValue("objectMapper", objectMapper);
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 401, 422, 450, 500})
    void decode_shouldThrownFeastClientException(int status) {

        Response response =
                getTestFeignResponse(TEST_MESSAGE, Request.HttpMethod.POST, status);

        IOException exception =
                new IOException(TEST_MESSAGE);

        try (MockedStatic<IOUtils> ioUtilsMockedStatic = mockStatic(IOUtils.class)) {
            ioUtilsMockedStatic.when(() -> IOUtils.toString(any(InputStream.class), any(Charset.class)))
                    .thenThrow(exception);

            ExternalServiceClientException externalServiceClientException =
                    new ExternalServiceClientException(exception.getMessage());

            assertThat(reactiveFeignErrorDecoder.decode(TEST_MESSAGE, response))
                    .usingRecursiveComparison()
                    .isEqualTo(externalServiceClientException);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 401, 422, 450, 500})
    void decode_shouldReturnFeastClientException(int status)
            throws JsonProcessingException {

        ErrorExternalServiceModel errorExternalServiceModel = TestDataBuilder.getTestErrorFeastModel();

        Response response =
                getTestFeignResponse(objectMapper.writeValueAsString(errorExternalServiceModel), Request.HttpMethod.POST, status);

        ExternalServiceClientException externalServiceClientException =
                new ExternalServiceClientException(errorExternalServiceModel.detail());

        assertThat(reactiveFeignErrorDecoder.decode(TEST_MESSAGE, response))
                .usingRecursiveComparison()
                .isEqualTo(externalServiceClientException);
    }

    @ParameterizedTest
    @ValueSource(ints = {399, 501, 502, 505, 550})
    void decode_shouldReturnFeastServerException() {

        Response response =
                getTestFeignResponse(TEST_MESSAGE, Request.HttpMethod.POST, 505);

        ExternalServiceServerException feastServerException =
                new ExternalServiceServerException("Ошибка при вызове " + TEST_MESSAGE);

        assertThat(reactiveFeignErrorDecoder.decode(TEST_MESSAGE, response))
                .usingRecursiveComparison()
                .isEqualTo(feastServerException);
    }

}