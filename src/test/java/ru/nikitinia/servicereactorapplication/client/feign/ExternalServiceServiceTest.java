package ru.nikitinia.servicereactorapplication.client.feign;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceConnectionException;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExternalServiceService.class)
class ExternalServiceServiceTest {

    @Autowired
    private ExternalServiceService externalServiceService;

    @MockitoBean
    private ExternalServiceClient externalServiceClient;

    private final ByteArrayOutputStream byteArrayOutputStream =
            new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @Test
    void checkFeastService() {
        assertThat(externalServiceService)
                .hasFieldOrPropertyWithValue("externalServiceClient", externalServiceClient)
                .hasNoNullFieldsOrProperties()
        ;
    }

    @Test
    void getFeastPersonResponse_shouldReturnResult() {
        final ExternalServiceRequest externalServiceRequest = TestDataBuilder.getTestFeastPersonRequest();
        final ExternalServiceResponse externalServiceResponse = TestDataBuilder.getTestFeastPersonResponse();
        final Flux<ExternalServiceResponse> responseFlux = Flux.just(externalServiceResponse);

        when(externalServiceClient.getFeastPersonResponse(externalServiceRequest))
                .thenReturn(responseFlux);


        StepVerifier
                .create(externalServiceService.getFeastPersonResponse(externalServiceRequest))
                .expectNextMatches(feastResponseExpected -> feastResponseExpected.equals(externalServiceResponse))
                .verifyComplete();

        assertThat(byteArrayOutputStream.toString().trim())
                .contains("Duration call")
                .contains("for")
                .contains(externalServiceRequest.toString())
                .contains("PT")
                .contains(".")
                .contains("S");

        verify(externalServiceClient, times(1)).getFeastPersonResponse(externalServiceRequest);

    }

    @Test
    void getFeastPersonResponse_shouldThrownReactiveFeignException() {
        final ExternalServiceRequest externalServiceRequest = TestDataBuilder.getTestFeastPersonRequest();
        final RuntimeException exception = new RuntimeException(TEST_MESSAGE);

        when(externalServiceClient.getFeastPersonResponse(externalServiceRequest))
                .thenReturn(Flux.error(exception));

        StepVerifier
                .create(externalServiceService.getFeastPersonResponse(externalServiceRequest))
                .expectErrorMatches(
                        throwable -> throwable instanceof ExternalServiceConnectionException
                                && throwable.getMessage().contains(String.format("Ошибка соединения: %s", exception.getMessage()))
                )
                .verify();

        verify(externalServiceClient, times(1)).getFeastPersonResponse(externalServiceRequest);
    }

}