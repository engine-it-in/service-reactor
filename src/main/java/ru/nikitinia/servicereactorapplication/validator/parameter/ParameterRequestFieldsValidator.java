package ru.nikitinia.servicereactorapplication.validator.parameter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE;


@Component
public class ParameterRequestFieldsValidator implements ConstraintValidator<ParameterRequestFieldsMatch, Parameter> {
    @Override
    public boolean isValid(Parameter parameter, ConstraintValidatorContext constraintValidatorContext) {

        AtomicBoolean isValid = new AtomicBoolean(true);
        Arrays.stream(ValidationParametersEnum.values())
                .forEach(
                        validationItem -> {
                            if (parameter.className().equals(validationItem.getClassName())
                                    && parameter.name().equals(validationItem.getName())
                                    && !parameter.value().matches(validationItem.getPatternForMatch())) {

                                createExceptionText(validationItem.getClassName(), parameter.value(), constraintValidatorContext);
                                isValid.set(false);

                            }
                        }
                );

        return isValid.get();
    }

    private void createExceptionText(
            String fieldName,
            String fieldValue,
            ConstraintValidatorContext constraintValidatorContext) {

        constraintValidatorContext.disableDefaultConstraintViolation();

        constraintValidatorContext
                .buildConstraintViolationWithTemplate(
                        String.format(
                                VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE,
                                fieldName,
                                fieldValue)
                )
                .addConstraintViolation();
    }
}