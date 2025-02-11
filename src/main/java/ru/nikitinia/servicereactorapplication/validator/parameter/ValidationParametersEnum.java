package ru.nikitinia.servicereactorapplication.validator.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.nikitinia.servicereactorapplication.util.Constant.ValidationParameters.*;


@AllArgsConstructor
@Getter
public enum ValidationParametersEnum {

    INN(PARAMETER_CLASS_NAME_INN, PARAMETER_NAME_ENTITY_ID, PATTERN_INN),
    PERSON_KEY(PARAMETER_CLASS_NAME_PERSON_KEY, PARAMETER_NAME_ENTITY_ID, PATTERN_PERSON_KEY),
    CLAIM_SUBCLAIM(PARAMETER_CLASS_NAME_CLAIM_SUBCLAIM, PARAMETER_NAME_ENTITY_ID, PATTERN_CLAIM_SUBCLAIM);

    private final String className;
    private final String name;
    private final String patternForMatch;

}
