package ru.nikitinia.servicereactorapplication.model.service.request;

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
class RequestTest {

    @Autowired
    JacksonTester<Request> jacksonTester;

    @Value("classpath:json/request.json")
    Resource jsonRequest;

    private final Request objectRequest =
            TestDataBuilder.getTestRequestWithOutEntityId();

    @Test
    void shouldSerializeObjectToJson() throws IOException {
        assertThat(jacksonTester.write(objectRequest))
                .isStrictlyEqualToJson(jsonRequest);
    }

    @Test
    void shouldDeserializeJsonToObject() throws IOException {
        assertThat(jacksonTester.read(jsonRequest))
                .usingRecursiveComparison()
                .isEqualTo(objectRequest);
    }

}