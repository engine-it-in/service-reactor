package ru.nikitinia.servicereactorapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nikitinia.servicereactorapplication.client.feign.ExternalServiceService;
import ru.nikitinia.servicereactorapplication.mapper.ExternalServiceResponseToResponseMapper;
import ru.nikitinia.servicereactorapplication.mapper.RequestToExternalServiceRequestMapper;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.model.service.response.Response;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceReactorService {

    private final RequestToExternalServiceRequestMapper requestToExternalServiceRequestMapper;
    private final ExternalServiceResponseToResponseMapper externalServiceResponseToResponseMapper;
    private final ExternalServiceService externalServiceService;

    public Mono<Response> processingFeast(Request request) {
        return externalServiceService
                .getFeastPersonResponse(requestToExternalServiceRequestMapper.mapRequestToFeast(request))
                .map(externalServiceResponseToResponseMapper::mapFeastResponseToResponse)
                .singleOrEmpty();
    }

}
