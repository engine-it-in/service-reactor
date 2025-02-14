package ru.nikitinia.servicereactorapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.model.service.response.Response;
import ru.nikitinia.servicereactorapplication.service.ServiceReactorService;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.FEATURE_CHECK_PATH;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "external-service.online-features.url=url",
        "external-service.online-features.path=/string",
})
@WebFluxTest(controllers = ExternalServiceController.class)
class ExternalServiceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ServiceReactorService serviceReactorService;

    @Test
    void checkFeast_shouldReturnSuccessAnswer() {
        final Request request = TestDataBuilder.getTestRequestWithOneEntityId();
        final Response response = TestDataBuilder.getTestResponse();

        when(serviceReactorService.processingFeast(request))
                .thenReturn(Mono.just(response));

        webTestClient
                .post()
                .uri(FEATURE_CHECK_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Response.class)
                .hasSize(1)
                .contains(response);

        verify(serviceReactorService).processingFeast(request);

    }

}