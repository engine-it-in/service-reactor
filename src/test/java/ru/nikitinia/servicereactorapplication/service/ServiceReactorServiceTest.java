package ru.nikitinia.servicereactorapplication.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.nikitinia.servicereactorapplication.client.feign.ExternalServiceService;
import ru.nikitinia.servicereactorapplication.mapper.ExternalServiceResponseToResponseMapper;
import ru.nikitinia.servicereactorapplication.mapper.RequestToExternalServiceRequestMapper;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.model.service.response.Response;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceReactorService.class})
class ServiceReactorServiceTest {

    @Autowired
    private ServiceReactorService serviceReactorService;

    @MockitoBean
    private RequestToExternalServiceRequestMapper requestToExternalServiceRequestMapper;

    @MockitoBean
    private ExternalServiceResponseToResponseMapper externalServiceResponseToResponseMapper;

    @MockitoBean
    private ExternalServiceService externalServiceService;

    @Test
    void checkFeastOnlineCheckService() {

        assertThat(serviceReactorService)
                .hasFieldOrPropertyWithValue("externalServiceService", externalServiceService)
                .hasFieldOrPropertyWithValue("requestToExternalServiceRequestMapper", requestToExternalServiceRequestMapper)
                .hasFieldOrPropertyWithValue("externalServiceResponseToResponseMapper", externalServiceResponseToResponseMapper)
                .hasNoNullFieldsOrProperties()
        ;

    }

    @Test
    void processingFeast_shouldReturnResult() {

        final Request request = TestDataBuilder.getTestRequestWithOutEntityId();
        final Response response = TestDataBuilder.getTestResponse();
        final ExternalServiceRequest externalServiceRequest = TestDataBuilder.getTestFeastPersonRequest();
        final ExternalServiceResponse externalServiceResponse = TestDataBuilder.getTestFeastPersonResponse();

        when(requestToExternalServiceRequestMapper.mapRequestToFeast(request))
                .thenReturn(externalServiceRequest);

        when(externalServiceResponseToResponseMapper.mapFeastResponseToResponse(externalServiceResponse))
                .thenReturn(response);

        when(externalServiceService.getFeastPersonResponse(externalServiceRequest))
                .thenReturn(Flux.just(externalServiceResponse));

        StepVerifier
                .create(serviceReactorService.processingFeast(request))
                .expectNextMatches(responseData -> responseData.equals(response))
                .verifyComplete();

        verify(requestToExternalServiceRequestMapper).mapRequestToFeast(request);
        verify(externalServiceService).getFeastPersonResponse(externalServiceRequest);
        verify(externalServiceResponseToResponseMapper).mapFeastResponseToResponse(externalServiceResponse);
    }

}