package ru.nikitinia.servicereactorapplication.model.external.response;

import org.junit.jupiter.api.Test;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalServiceResponseTest {

    private final ExternalServiceResponse externalServiceResponse =
            TestDataBuilder.getTestFeastPersonResponse();

    @Test
    void checkFeastResponse() {
        assertThat(externalServiceResponse)
                .isInstanceOf(Serializable.class);
    }

}