package ru.nikitinia.servicereactorapplication.mapper.component;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nikitinia.servicereactorapplication.exception.ServiceReactorLogicException;
import ru.nikitinia.servicereactorapplication.util.TestConstant;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.PERSON_KEY_FORMAT_EXCEPTION;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersonKeyDataNormalizer.class})
class PersonKeyDataNormalizerTest {

    @Autowired
    private PersonKeyDataNormalizer personKeyDataNormalizer;

    @ParameterizedTest
    @EnumSource(TestConstant.DictionaryRawAndNormalizedValue.class)
    void normalizeParameter_shouldReturnNormalizedParameter(TestConstant.DictionaryRawAndNormalizedValue dictionaryRawAndNormalizedValueEnum) {

        final HashMap<String, List<String>> entityActual =
                TestDataBuilder.getTestValidPersonKeyMapWithValue(dictionaryRawAndNormalizedValueEnum.getRawValue());
        final HashMap<String, List<String>> entityExpected =
                TestDataBuilder.getTestValidPersonKeyMapWithValue(dictionaryRawAndNormalizedValueEnum.getNormalizedValue());

        assertThat(personKeyDataNormalizer.normalizeParameter(entityActual))
                .usingRecursiveComparison()
                .isEqualTo(entityExpected);

    }

    @ParameterizedTest
    @EnumSource(TestConstant.DictionaryBadPersonKey.class)
    void normalizeParameter_shouldThrownExceptionBecauseDateNotFormat(TestConstant.DictionaryBadPersonKey dictionaryBadPersonKey) {

        final HashMap<String, List<String>> entity =
                TestDataBuilder.getTestValidPersonKeyMapWithValue(dictionaryBadPersonKey.getValue());

        final ServiceReactorLogicException exception = new ServiceReactorLogicException(String.format(PERSON_KEY_FORMAT_EXCEPTION, dictionaryBadPersonKey.getValue()));


        assertThatThrownBy(() -> personKeyDataNormalizer.normalizeParameter(entity))
                .isInstanceOf(exception.getClass())
                .hasMessage(exception.getMessage());


    }

}