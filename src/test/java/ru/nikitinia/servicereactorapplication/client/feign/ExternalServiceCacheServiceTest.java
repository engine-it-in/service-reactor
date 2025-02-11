package ru.nikitinia.servicereactorapplication.client.feign;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import redis.embedded.RedisServer;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.model.service.response.Response;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = ExternalServiceCacheServiceTest.EmbeddedRedisConfiguration.class
)
@EnableCaching
class ExternalServiceCacheServiceTest {

    @Autowired
    private ExternalServiceService externalServiceService;

    @MockitoBean
    private ExternalServiceClient externalServiceClient;

    @Test
    void checkCacheHitsManyTimeButClientCacheOne() {

        final Request request = TestDataBuilder.getTestRequestWithOutEntityId();
        final Response response = TestDataBuilder.getTestResponse();
        final ExternalServiceRequest externalServiceRequest = TestDataBuilder.getTestFeastPersonRequest();
        final ExternalServiceResponse externalServiceResponse = TestDataBuilder.getTestFeastPersonResponse();

        when(externalServiceClient.getFeastPersonResponse(externalServiceRequest))
                .thenReturn(Flux.just(externalServiceResponse));

        StepVerifier
                .create(externalServiceService.getFeastPersonResponse(externalServiceRequest))
                .expectNextMatches(externalServiceResponseData -> externalServiceResponseData.equals(externalServiceResponse))
                .verifyComplete();

        verify(externalServiceClient).getFeastPersonResponse(externalServiceRequest);

        IntStream.range(0, 10)
                .forEach(
                        doIt -> StepVerifier
                                .create(externalServiceService.getFeastPersonResponse(externalServiceRequest))
                                .expectNextMatches(externalServiceResponseData -> externalServiceResponseData.equals(externalServiceResponse))
                                .verifyComplete()
                );

        verify(externalServiceClient).getFeastPersonResponse(externalServiceRequest);

    }

    @TestConfiguration
    @RequiredArgsConstructor
    static class EmbeddedRedisConfiguration {

        private RedisServer redisServer;

        private final RedisProperties redisProperties;

        @PostConstruct
        public void start() throws IOException {
            redisServer = new RedisServer(redisProperties.getPort());
            redisServer.start();
        }

        @PreDestroy
        public void stop() throws IOException {
            this.redisServer.stop();

        }

    }

}