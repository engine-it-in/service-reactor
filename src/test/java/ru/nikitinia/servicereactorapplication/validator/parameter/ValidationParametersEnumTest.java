package ru.nikitinia.servicereactorapplication.validator.parameter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nikitinia.servicereactorapplication.util.Constant.ValidationParameters.*;

class ValidationParametersEnumTest {

    @ParameterizedTest
    @EnumSource(ValidationParametersEnum.class)
    void checkValidationParametersEnum(ValidationParametersEnum parametersEnum) {
        assertThat(ValidationParametersEnum.values())
                .contains(parametersEnum);
    }

    @Test
    void checkValidationParametersEnumClassName() {
        assertThat(Arrays.stream(ValidationParametersEnum.values()).map(ValidationParametersEnum::getClassName).toList())
                .containsOnly(
                        PARAMETER_CLASS_NAME_INN,
                        PARAMETER_CLASS_NAME_PERSON_KEY,
                        PARAMETER_CLASS_NAME_CLAIM_SUBCLAIM)
        ;
    }

    @Test
    void checkValidationParametersEnumName() {
        assertThat(Arrays.stream(ValidationParametersEnum.values()).map(ValidationParametersEnum::getName).toList())
                .hasSize(3)
                .containsOnly(
                        PARAMETER_NAME_ENTITY_ID
                )
        ;
    }

    @Test
    void checkValidationParametersEnumPatternForMatch() {
        assertThat(Arrays.stream(ValidationParametersEnum.values()).map(ValidationParametersEnum::getPatternForMatch).toList())
                .containsOnly(
                        PATTERN_INN,
                        PATTERN_PERSON_KEY,
                        PATTERN_CLAIM_SUBCLAIM
                )
        ;
    }

}