package ru.nikitinia.servicereactorapplication.model.error;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
class ErrorModelTest {

    @Autowired
    JacksonTester<ErrorModel> jacksonTester;

    @Value("classpath:json/error-model.json")
    Resource jsonErrorModel;

    private final ErrorModel objectErrorModel
            = TestDataBuilder.getTestErrorModel();

    @Test
    void shouldSerializeObjectToJson() throws Exception {
        assertThat(jacksonTester.write(objectErrorModel))
                .isStrictlyEqualToJson(jsonErrorModel);
    }

    @Test
    void shouldDeserializeJsonToObject() throws Exception {
        assertThat(jacksonTester.read(jsonErrorModel))
                .usingRecursiveComparison()
                .isEqualTo(objectErrorModel);
    }

}