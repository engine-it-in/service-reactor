package ru.nikitinia.servicereactorapplication.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;

class ExternalServiceConnectionExceptionTest {

    private final ExternalServiceConnectionException feastException =
            new ExternalServiceConnectionException(TEST_MESSAGE);

    @Test
    void feastOnlineLogicException_shouldDo() {
        assertThat(feastException)
                .hasFieldOrPropertyWithValue("message", TEST_MESSAGE)
                .isInstanceOf(RuntimeException.class);
    }

}