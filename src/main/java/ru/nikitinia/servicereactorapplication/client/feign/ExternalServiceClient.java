package ru.nikitinia.servicereactorapplication.client.feign;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import ru.nikitinia.servicereactorapplication.client.feign.config.ReactiveFeignConfig;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;

@ReactiveFeignClient(
        name = "externalServiceClient",
        url = "${external-service.online-features.url}",
        configuration = ReactiveFeignConfig.class
)
public interface ExternalServiceClient {

    @PostMapping(
            value = "${external-service.online-features.path}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Flux<ExternalServiceResponse> getFeastPersonResponse(@RequestBody ExternalServiceRequest personRequest);

}
