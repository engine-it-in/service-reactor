package ru.nikitinia.servicereactorapplication.model.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ParameterTest {

    @Autowired
    JacksonTester<Parameter> jacksonTester;

    @Value("classpath:json/parameter.json")
    Resource jsonParameter;

    private final Parameter objectParameter =
            TestDataBuilder.getTestParameter();

    @Test
    void shouldSerializeObjectToJson() throws IOException {
        assertThat(jacksonTester.write(objectParameter))
                .isStrictlyEqualToJson(jsonParameter);
    }

    @Test
    void shouldDeserializeJsonToObject() throws IOException {
        assertThat(jacksonTester.read(jsonParameter))
                .usingRecursiveComparison()
                .isEqualTo(objectParameter);
    }

}
