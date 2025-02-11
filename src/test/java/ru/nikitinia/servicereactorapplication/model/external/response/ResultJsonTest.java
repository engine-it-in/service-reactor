package ru.nikitinia.servicereactorapplication.model.external.response;

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
class ResultJsonTest {

    @Autowired
    JacksonTester<Result> jacksonTester;

    @Value("classpath:json/result.json")
    Resource jsonResult;

    private final Result objectResult =
            TestDataBuilder.getTestResult();

    @Test
    void shouldSerializeObjectToJson() throws IOException {
        assertThat(jacksonTester.write(objectResult))
                .isStrictlyEqualToJson(jsonResult);
    }

    @Test
    void shouldDeserializeJsonToObject() throws IOException {
        assertThat(jacksonTester.read(jsonResult))
                .usingRecursiveComparison()
                .isEqualTo(objectResult);
    }

}