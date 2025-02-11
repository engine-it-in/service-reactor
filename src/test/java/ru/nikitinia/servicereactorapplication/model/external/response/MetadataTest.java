package ru.nikitinia.servicereactorapplication.model.external.response;

import org.junit.jupiter.api.Test;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

class MetadataTest {

    private final Metadata metadata =
            TestDataBuilder.getTestMetadata();

    @Test
    void checkMetadata() {
        assertThat(metadata)
                .isInstanceOf(Serializable.class);
    }

}