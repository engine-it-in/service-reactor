package ru.nikitinia.servicereactorapplication.model.external.response;

import org.junit.jupiter.api.Test;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    private final Result result =
            TestDataBuilder.getTestResult();

    @Test
    void checkResult() {
        assertThat(result)
                .isInstanceOf(Serializable.class);
    }

}