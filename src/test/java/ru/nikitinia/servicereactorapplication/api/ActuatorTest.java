package ru.nikitinia.servicereactorapplication.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class ActuatorTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(ApplicationContext context) {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void checkActuator() {
        webTestClient.get()
                .uri("/actuator")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$._links").exists();
    }

    @Test
    void checkActuatorMetrics()  {
        webTestClient.get()
                .uri("/actuator/metrics")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.names").isArray();
    }
}
