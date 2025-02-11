package ru.nikitinia.servicereactorapplication.validator.parameter;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoInteractions;
import static ru.nikitinia.servicereactorapplication.util.TestDataBuilder.getTestParameter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ParameterRequestFieldsValidator.class)
class ParameterRequestFieldsValidatorTest {

    @Autowired
    private ParameterRequestFieldsValidator parameterRequestFieldsValidator;

    @MockitoBean
    private ConstraintValidatorContext constraintValidatorContext;


    @Test
    void isValidTrue() {

        assertTrue(parameterRequestFieldsValidator.isValid(getTestParameter(), null));

        verifyNoInteractions(constraintValidatorContext);

    }

}