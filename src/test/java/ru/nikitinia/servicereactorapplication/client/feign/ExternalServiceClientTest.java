package ru.nikitinia.servicereactorapplication.client.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.hc.core5.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactivefeign.spring.config.ReactiveFeignAutoConfiguration;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.test.StepVerifier;
import ru.nikitinia.servicereactorapplication.client.feign.config.ReactiveFeignConfig;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceClientException;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceServerException;
import ru.nikitinia.servicereactorapplication.model.error.ErrorExternalServiceModel;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;


@SpringBootTest
@TestPropertySource
        (properties = {
                "external-service.online-features.url=http://localhost:${wiremock.server.port}",
                "external-service.online-features.path=/string",
                "spring.main.allow-bean-definition-overriding=true"
        })
@AutoConfigureWireMock(port = 0)
@ImportAutoConfiguration(classes = {
        ReactiveFeignAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
})
@EnableReactiveFeignClients(clients = {ExternalServiceClient.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ExternalServiceClientTest {

    @Autowired
    private ExternalServiceClient externalServiceClient;


    @Value("/string")
    private String url;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void checkFeastClientTest() {

        ReactiveFeignClient reactiveFeignClientAnnotation =
                ExternalServiceClient.class.getAnnotation(ReactiveFeignClient.class);

        assertThat(reactiveFeignClientAnnotation)
                .isInstanceOfSatisfying(ReactiveFeignClient.class, reactiveFeignClientData -> {

                    assertThat(reactiveFeignClientData.name())
                            .isEqualTo("externalServiceClient");

                    assertThat(reactiveFeignClientData.url())
                            .isEqualTo("${external-service.online-features.url}");

                    assertThat(reactiveFeignClientData.configuration())
                            .contains(ReactiveFeignConfig.class);

                });

    }

    @Test
    void getFeastResponse_returnOk() throws JsonProcessingException {
        final ExternalServiceResponse externalServiceResponse = TestDataBuilder.getTestFeastPersonResponse();
        final ExternalServiceRequest externalServiceRequest = TestDataBuilder.getTestFeastPersonRequest();

        stubFor(post(urlEqualTo(url))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody(objectMapper.writeValueAsString(externalServiceResponse))));

        StepVerifier
                .create(externalServiceClient.getFeastPersonResponse(externalServiceRequest))
                .expectNextMatches(feastResponseData -> feastResponseData.equals(externalServiceResponse))
                .verifyComplete();

        verify(1, postRequestedFor(urlEqualTo(url)));
    }

    @Test
    void getFeastResponse_shouldThrown4xxStatuses() throws JsonProcessingException {
        final ExternalServiceRequest externalServiceRequest = TestDataBuilder.getTestFeastPersonRequest();
        final ErrorExternalServiceModel errorExternalServiceModel = TestDataBuilder.getTestErrorFeastModel();

        stubFor(post(urlEqualTo(url))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(
                        aResponse()
                                .withStatus(422)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody(objectMapper.writeValueAsString(errorExternalServiceModel))));

        StepVerifier
                .create(externalServiceClient.getFeastPersonResponse(externalServiceRequest))
                .expectErrorMatches(
                        throwable -> throwable instanceof ExternalServiceClientException
                                && throwable.getMessage().equals(errorExternalServiceModel.detail())
                )
                .verify();

        verify(postRequestedFor(urlEqualTo(url)));

    }

    @Test
    void getFeastResponse_shouldThrown5xxStatusesException() {
        final ExternalServiceRequest externalServiceRequest = TestDataBuilder.getTestFeastPersonRequest();

        stubFor(post(urlEqualTo(url))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(
                        aResponse()
                                .withStatus(501)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody(TEST_MESSAGE)));

        StepVerifier
                .create(externalServiceClient.getFeastPersonResponse(externalServiceRequest))
                .expectErrorMatches(
                        throwable ->
                                throwable instanceof ExternalServiceServerException
                                        && throwable.getMessage().equals("Ошибка при вызове ExternalServiceClient#getFeastPersonResponse(ExternalServiceRequest)")
                ).verify();

        verify(postRequestedFor(urlEqualTo(url)));
    }

}