package ru.nikitinia.servicereactorapplication.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    @UtilityClass
    public class ControllerTimedMetrics {
        public static final String TIME_METRIC_NAME = "controller.processing.timer";
        public static final String TIME_METRIC_DESCRIPTION = "Time taken to process controller Api";
    }

    @UtilityClass
    public class Feast {
        public static final String FEATURESET = "featureset";
        public static final String FEATURE = "feature";
        public static final String ENTITY_ID = "entity_id";
    }

    @UtilityClass
    public class NormalizeValue {
        public static final String COLON = ":";
    }

    @UtilityClass
    public class ExceptionText {

        @UtilityClass
        public class Feast {
            public static final String ENTITY_ID_NO_EXCEPTION = "Без entity_id запрос не обрабатывается";
            public static final String PERSON_KEY_FORMAT_EXCEPTION = "Значение person_key - %s, передано не в формате: Фамилия Имя Отчество yyyy-MM-dd";
            public static final String ENTITY_ID_MORE_ONE_EXCEPTION = "Более одного entity_id не обрабатывается";
            public static final String VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE = "Поле: %s содержит невалидное значение - %s";
        }

    }

    @UtilityClass
    public class ValidationParameters {

        public static final String PARAMETER_NAME_ENTITY_ID = "entity_id";
        public static final String PARAMETER_CLASS_NAME_INN = "inn";
        public static final String PARAMETER_CLASS_NAME_PERSON_KEY = "person_key";
        public static final String PARAMETER_NAME_PERSON_KEY = "person_key";
        public static final String PARAMETER_CLASS_NAME_CLAIM_SUBCLAIM = "claim_subclaim";
        public static final String PATTERN_INN = "\\d{10}";
        public static final String PATTERN_PERSON_KEY = "([а-яА-Я|Ё|ё]+\\s){2,3}\\d{4}-\\d{2}-\\d{2}";
        public static final String PATTERN_CLAIM_SUBCLAIM = "\\w{0,9}/\\w{0,9}/\\w{0,9}/\\w{0,9}_\\d{0,9}";
    }

    @UtilityClass
    public class MainValue {
        public static final String SEPARATOR_FOR_METHOD_NOT_ALLOWED_ARGUMENT = ", ";
    }

    @UtilityClass
    public class PersonKeyNormalizer {
        public static final int DATE_LENGTH = 10;
        public static final int NULL_VALUE = 0;
        public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";
    }

    @UtilityClass
    public class CacheProperties {
        public static final String CACHE_NAME = "external-service";
    }

}