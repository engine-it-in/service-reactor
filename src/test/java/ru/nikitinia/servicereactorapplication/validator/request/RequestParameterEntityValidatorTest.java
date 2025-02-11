package ru.nikitinia.servicereactorapplication.validator.request;

import jakarta.validation.ConstraintValidator;
import org.junit.jupiter.api.Test;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParameterEntityValidatorTest {

    private final RequestParameterEntityValidator requestParameterEntityValidator =
            new RequestParameterEntityValidator();

    @Test
    void checkRequestParameterEntityValidator() {
        assertThat(requestParameterEntityValidator)
                .isInstanceOf(ConstraintValidator.class)
                .hasAllNullFieldsOrProperties();
    }

    @Test
    void isValid_oneEntityId() {
        final Request request = TestDataBuilder.getTestRequestWithOneEntityId();
        assertThat(requestParameterEntityValidator.isValid(request, null))
                .isTrue();
    }

}