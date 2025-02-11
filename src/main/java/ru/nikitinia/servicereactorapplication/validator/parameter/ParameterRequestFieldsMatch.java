package ru.nikitinia.servicereactorapplication.validator.parameter;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Target({ElementType.TYPE})
@Constraint(validatedBy = ParameterRequestFieldsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterRequestFieldsMatch {

    String message() default EMPTY;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
