package ru.nikitinia.servicereactorapplication.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.nikitinia.servicereactorapplication.controller.ExternalServiceController;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceClientException;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceConnectionException;
import ru.nikitinia.servicereactorapplication.exception.ServiceReactorLogicException;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceServerException;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.service.ServiceReactorService;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.*;
import static ru.nikitinia.servicereactorapplication.util.Constant.ValidationParameters.*;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.FEATURE_CHECK_PATH;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.ValidationConstant.*;


@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "feast.url.online-features=url",
        "feast.path.online-features=get-online-features"
})
@WebFluxTest(controllers = ExternalServiceController.class)
class ExternalServiceHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ServiceReactorService serviceReactorService;

    @ParameterizedTest
    @MethodSource("provideExceptionDataForFeast")
    void feastOnlineCheckHandler_shouldHandleException(int status, Exception exception) {

        final Request request = TestDataBuilder.getTestRequestWithOneEntityId();

        when(serviceReactorService.processingFeast(request))
                .thenThrow(exception);

        webTestClient
                .post()
                .uri(FEATURE_CHECK_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(status)
                .expectBody().jsonPath("$.message").isEqualTo(exception.getMessage())
        ;

        verify(serviceReactorService).processingFeast(request);
    }

    private static Stream<Arguments> provideExceptionDataForFeast() {
        return Stream.of(
                Arguments.of(400, new ServiceReactorLogicException(TEST_MESSAGE)),
                Arguments.of(422, new ExternalServiceClientException(TEST_MESSAGE)),
                Arguments.of(422, new ExternalServiceConnectionException(TEST_MESSAGE)),
                Arguments.of(500, new ExternalServiceServerException(TEST_MESSAGE))
        );
    }

    @ParameterizedTest
    @MethodSource("provideTextData")
    void feastOnlineCheckHandler_shouldMethodNotAllowedExceptionWithMoreEntityId(String exceptionText) {
        final Request request = TestDataBuilder.getTestRequestWithNotAllowedParameters();

        webTestClient
                .post()
                .uri(FEATURE_CHECK_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("$.message")
                .value(
                        Matchers.containsString(exceptionText)
                );
    }

    private static Stream<Arguments> provideTextData() {
        return Stream.of(
                Arguments.of(
                        ENTITY_ID_MORE_ONE_EXCEPTION
                ),
                Arguments.of(
                        String.format(
                                VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE,
                                PARAMETER_CLASS_NAME_INN,
                                INN_NOT_VALID)
                ),
                Arguments.of(
                        String.format(
                                VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE,
                                PARAMETER_CLASS_NAME_CLAIM_SUBCLAIM,
                                CLAIM_SUBCLAIM_NOT_VALID)
                ),
                Arguments.of(
                        String.format(
                                VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE,
                                PARAMETER_CLASS_NAME_PERSON_KEY,
                                PERSON_KEY_NOT_VALID)
                )

        );
    }

    @Test
    void feastOnlineCheckHandler_shouldWebExchangeBindExceptionWithOutEntityId() {
        final Request request = TestDataBuilder.getTestRequestWithOutEntityId();

        webTestClient
                .post()
                .uri(FEATURE_CHECK_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("$.message")
                .value(Matchers.containsString(ENTITY_ID_NO_EXCEPTION));
    }


}