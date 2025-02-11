package ru.nikitinia.servicereactorapplication.model.error;

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
class ErrorExternalServiceModelTest {

    @Autowired
    JacksonTester<ErrorExternalServiceModel> jacksonTester;

    @Value("classpath:json/error-feast-model.json")
    private Resource jsonErrorFeastModel;

    private final ErrorExternalServiceModel objectErrorExternalServiceModel = TestDataBuilder.getTestErrorFeastModel();

    @Test
    void shouldSerializeObjectToJson() throws IOException {
        assertThat(jacksonTester.write(objectErrorExternalServiceModel))
                .isStrictlyEqualToJson(jsonErrorFeastModel);
    }

    @Test
    void shouldDeserializeJsonToObject() throws Exception {
        assertThat(jacksonTester.read(jsonErrorFeastModel))
                .usingRecursiveComparison()
                .isEqualTo(objectErrorExternalServiceModel);
    }

}