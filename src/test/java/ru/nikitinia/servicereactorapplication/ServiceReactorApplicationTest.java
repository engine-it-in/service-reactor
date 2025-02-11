package ru.nikitinia.servicereactorapplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServiceReactorApplicationTest {

    @Autowired
    private ServiceReactorApplication application;

    @Test
    void contextLoad() {
        assertThat(application)
                .isNotNull();
    }

}