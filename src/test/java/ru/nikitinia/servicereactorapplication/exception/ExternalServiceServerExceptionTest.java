package ru.nikitinia.servicereactorapplication.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;

class ExternalServiceServerExceptionTest {

    private final ExternalServiceServerException feastException =
            new ExternalServiceServerException(TEST_MESSAGE);

    @Test
    void externalServiceServerException_shouldDo() {
        assertThat(feastException)
                .hasFieldOrPropertyWithValue("message", TEST_MESSAGE)
                .isInstanceOf(RuntimeException.class);
    }

}
