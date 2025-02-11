package ru.nikitinia.servicereactorapplication.cache.properties;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(value = "cache")
public record CacheProperties(

        Integer ttl

) {
}
