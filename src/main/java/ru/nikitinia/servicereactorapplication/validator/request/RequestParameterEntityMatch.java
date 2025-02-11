package ru.nikitinia.servicereactorapplication.validator.request;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.apache.commons.lang3.StringUtils.EMPTY;


@Target(ElementType.TYPE)
@Constraint(validatedBy = RequestParameterEntityValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParameterEntityMatch {

    String message() default EMPTY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
