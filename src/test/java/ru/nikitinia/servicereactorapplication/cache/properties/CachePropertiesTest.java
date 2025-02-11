package ru.nikitinia.servicereactorapplication.cache.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "cache.ttl=1"
})
class CachePropertiesTest {

    @Autowired
    private CacheProperties cacheProperties;

    private final CacheProperties cachePropertiesValues =
            CacheProperties.builder()
                    .ttl(1)
                    .build();

    @Test
    void checkCacheProperties() {
        assertThat(cacheProperties)
                .usingRecursiveComparison()
                .isEqualTo(cachePropertiesValues);
    }

}