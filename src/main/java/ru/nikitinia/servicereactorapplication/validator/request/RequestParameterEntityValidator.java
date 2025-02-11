package ru.nikitinia.servicereactorapplication.validator.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;

import java.util.concurrent.atomic.AtomicBoolean;

import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.ENTITY_ID_MORE_ONE_EXCEPTION;
import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.ENTITY_ID_NO_EXCEPTION;
import static ru.nikitinia.servicereactorapplication.util.Constant.Feast.ENTITY_ID;

public class RequestParameterEntityValidator implements ConstraintValidator<RequestParameterEntityMatch, Request> {
    @Override
    public boolean isValid(Request request, ConstraintValidatorContext context) {
        AtomicBoolean isValid = new AtomicBoolean(true);
        int quantityEntityId = request.parameter().stream()
                .filter(parameter -> parameter.name().equals(ENTITY_ID))
                .toList()
                .size();

        if (quantityEntityId == 0) {
            createExceptionText(ENTITY_ID_NO_EXCEPTION, context);
            isValid.set(false);
        } else if (quantityEntityId > 1) {
            createExceptionText(ENTITY_ID_MORE_ONE_EXCEPTION, context);
            isValid.set(false);
        }
        return isValid.get();
    }

    private void createExceptionText(String text, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(text)
                .addConstraintViolation();
    }
}
