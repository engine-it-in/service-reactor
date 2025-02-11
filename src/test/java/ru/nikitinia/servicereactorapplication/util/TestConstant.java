package ru.nikitinia.servicereactorapplication.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstant {

    public static final String FEATURE_CHECK_PATH = "/api/v1/do-it";
    public static final String TEST_MESSAGE = "message";


    public class ValidationConstant {
        public static final String INN_NOT_VALID = "123";
        public static final String PERSON_KEY_NOT_VALID = "Никитин 1986-12-09";
        public static final String CLAIM_SUBCLAIM_NOT_VALID = " 0398/046/01543/24_";
    }

    @AllArgsConstructor
    @Getter
    public enum DictionaryRawAndNormalizedValue {

        NORMALIZE_ALL_PARAMETER("Никитйн-Мейдель Иван Андрёевич Оглы 1986-12-09", "НИКИТИНМЕИДЕЛЬ ИВАН АНДРЕЕВИЧОГЛЫ 1986-12-09"),
        NORMALIZE_WITHOUT_MIDDLE_NAME("Никитин Иван 1986-12-09", "НИКИТИН ИВАН 1986-12-09"),
        NORMALIZE_ONLY_WITH_NAME("Никитин 1986-12-09", "НИКИТИН 1986-12-09");

        private final String rawValue;
        private final String normalizedValue;

    }

    @AllArgsConstructor
    @Getter
    public enum DictionaryBadPersonKey {

        PERSON_KEY_NOT_VALID_DATE_MONTH("Никитин Иван Андреевич 1986-13-01"), PERSON_KEY_NOT_VALID_DATE_DAY("Никитин Иван Андреевич 1986-12-32"), PERSON_KEY_NOT_VALID_ONLY_DATE("1986-12-32"), PERSON_KEY_NOT_VALID_ONLY_SURNAME("Никитин");

        private final String value;

    }


}
