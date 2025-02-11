package ru.nikitinia.servicereactorapplication.mapper.component;

import org.springframework.stereotype.Component;
import ru.nikitinia.servicereactorapplication.exception.ServiceReactorLogicException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.PERSON_KEY_FORMAT_EXCEPTION;
import static ru.nikitinia.servicereactorapplication.util.Constant.PersonKeyNormalizer.*;
import static ru.nikitinia.servicereactorapplication.util.Constant.ValidationParameters.PARAMETER_NAME_PERSON_KEY;

@Component
public class PersonKeyDataNormalizer {

    public Map<String, List<String>> normalizeParameter(Map<String, List<String>> dataForNormalize) {
        return dataForNormalize.entrySet().stream()
                .map(stringListEntry -> normalizeListValueForPersonKey(stringListEntry.getKey(), stringListEntry.getValue()))
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ));
    }

    private Map.Entry<String, List<String>> normalizeListValueForPersonKey(String key, List<String> value) {
        if (key.equals(PARAMETER_NAME_PERSON_KEY)) {
            value = value.stream()
                    .map(this::normalizeValue)
                    .toList();
        }
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private String normalizeValue(String value) {
        try {
            String fio = value.substring(NULL_VALUE, value.length() - DATE_LENGTH);
            String date = value.substring(value.length() - DATE_LENGTH);
            LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

            String[] fioList = fio.split(SPACE);

            List<String> surnameAndNameValue = new ArrayList<>();
            List<String> middleNameValue = new ArrayList<>();

            for (int i = 0; i < fioList.length; i++) {
                if (i == 0) {
                    surnameAndNameValue.add(fioList[i]);
                } else if (i == 1) {
                    surnameAndNameValue.add(fioList[i]);
                } else {
                    middleNameValue.add(fioList[i]);
                }
            }

            surnameAndNameValue.add(String.join(EMPTY, middleNameValue));

            String fullNameNormalize =
                    String.join(SPACE, surnameAndNameValue)
                            .toUpperCase()
                            .replace("-", EMPTY)
                            .replace("Ё", "Е")
                            .replace("Й", "И");

            return String.join(SPACE, fullNameNormalize.trim(), date);
        } catch (Exception exception) {
            throw new ServiceReactorLogicException(String.format(PERSON_KEY_FORMAT_EXCEPTION, value));
        }
    }
}