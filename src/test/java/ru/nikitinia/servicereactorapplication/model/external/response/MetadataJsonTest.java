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
class MetadataJsonTest {

    @Autowired
    JacksonTester<Metadata> jacksonTester;

    @Value("classpath:json/metadata.json")
    Resource jsonMetadata;

    private final Metadata objectMetadata =
            TestDataBuilder.getTestMetadata();

    @Test
    void shouldSerializeObjectToJson() throws IOException {
        assertThat(jacksonTester.write(objectMetadata))
                .isStrictlyEqualToJson(jsonMetadata);
    }

    @Test
    void shouldDeserializeJsonToObject() throws IOException {
        assertThat(jacksonTester.read(jsonMetadata))
                .usingRecursiveComparison()
                .isEqualTo(objectMetadata);
    }

}