package ru.nikitinia.servicereactorapplication.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest
class ApiDocsTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(ApplicationContext context) {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void checkApiDocs() {
        webTestClient.get()
                .uri("/v3/api-docs")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
