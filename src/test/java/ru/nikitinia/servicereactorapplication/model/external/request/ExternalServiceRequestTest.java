package ru.nikitinia.servicereactorapplication.model.external.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ExternalServiceRequestTest {

    @Autowired
    JacksonTester<ExternalServiceRequest> jacksonTester;

    @Value("classpath:json/feast-person-request.json")
    Resource jsonFeastPersonRequest;

    private final ExternalServiceRequest objectExternalServiceRequest =
            TestDataBuilder.getTestFeastPersonRequest();

    @Test
    void shouldSerializeObjectToJson() throws Exception {
        assertThat(jacksonTester.write(objectExternalServiceRequest))
                .isStrictlyEqualToJson(jsonFeastPersonRequest);
    }

    @Test
    void shouldDeserializeJsonToObject() throws Exception {
        assertThat(jacksonTester.read(jsonFeastPersonRequest))
                .usingRecursiveComparison()
                .isEqualTo(objectExternalServiceRequest);
    }

}