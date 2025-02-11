package ru.nikitinia.servicereactorapplication.client.feign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceConnectionException;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;

import java.time.Duration;
import java.time.LocalDateTime;

import static ru.nikitinia.servicereactorapplication.util.Constant.CacheProperties.CACHE_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalServiceService {

    private final ExternalServiceClient externalServiceClient;

    @Cacheable(value = CACHE_NAME)
    public Flux<ExternalServiceResponse> getFeastPersonResponse(ExternalServiceRequest personRequest) {

        final LocalDateTime startTime =
                LocalDateTime.now();

        return externalServiceClient.getFeastPersonResponse(personRequest)
                .doOnNext(
                        logger -> log.info(
                                "Duration call {} for {}",
                                Duration.between(LocalDateTime.now(), startTime),
                                personRequest)
                )
                .onErrorMap(
                        error -> {
                            if (error instanceof RuntimeException)
                                return new ExternalServiceConnectionException(
                                        String.format(
                                                "Ошибка соединения: %s",
                                                error.getMessage()));
                            return error;
                        });
    }
}
