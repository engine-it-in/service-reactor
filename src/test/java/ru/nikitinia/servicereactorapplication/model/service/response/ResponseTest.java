package ru.nikitinia.servicereactorapplication.model.service.response;

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
class ResponseTest {

    @Autowired
    JacksonTester<Response> jacksonTester;

    @Value("classpath:json/response.json")
    Resource jsonResponse;

    private final Response objectResponse =
            TestDataBuilder.getTestResponse();

    @Test
    void shouldSerializeObjectToJson() throws IOException {
        assertThat(jacksonTester.write(objectResponse))
                .isStrictlyEqualToJson(jsonResponse);
    }

    @Test
    void shouldDeserializeJsonToObject() throws IOException {
        assertThat(jacksonTester.read(jsonResponse))
                .usingRecursiveComparison()
                .isEqualTo(objectResponse);
    }

}