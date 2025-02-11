package ru.nikitinia.servicereactorapplication.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;

class ExternalServiceClientExceptionTest {

    private final ExternalServiceClientException externalServiceClientException =
            new ExternalServiceClientException(TEST_MESSAGE);

    @Test
    void feastClientException_shouldDo() {
        assertThat(externalServiceClientException)
                .hasFieldOrPropertyWithValue("message", TEST_MESSAGE)
                .isInstanceOf(RuntimeException.class);
    }

}
