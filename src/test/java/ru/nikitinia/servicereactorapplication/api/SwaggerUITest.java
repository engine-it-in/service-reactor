package ru.nikitinia.servicereactorapplication.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class SwaggerUITest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(ApplicationContext context) {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void checkSwaggerUI() {
        webTestClient.get()
                .uri("/swagger-ui/index.html")
                .exchange()
                .expectStatus().isOk();

    }

}