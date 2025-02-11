package ru.nikitinia.servicereactorapplication.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import ru.nikitinia.servicereactorapplication.cache.properties.CacheProperties;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.nikitinia.servicereactorapplication.util.Constant.CacheProperties.CACHE_NAME;

@ExtendWith(MockitoExtension.class)
class CacheConfigTest {

    private CacheConfig cacheConfig;

    @Mock
    private CacheProperties cacheProperties;

    @BeforeEach
    void setUp() {
        cacheConfig = new CacheConfig(cacheProperties);
    }

    @Test
    void checkCacheConfig() {
        assertThat(cacheConfig)
                .hasFieldOrPropertyWithValue("cacheProperties", cacheProperties);
    }

    @Test
    void reactiveRedisTemplate_shouldConfigure() {

        assertThat(cacheConfig.reactiveRedisTemplate(new LettuceConnectionFactory()))
                .isInstanceOfSatisfying(ReactiveRedisTemplate.class, reactiveRedisTemplate -> {

                    assertThat(reactiveRedisTemplate.getConnectionFactory())
                            .isInstanceOf(LettuceConnectionFactory.class);

                    assertThat(reactiveRedisTemplate.getSerializationContext())
                            .isInstanceOf(RedisSerializationContext.class);

                });
    }

    @Test
    void cacheManager_shouldConfigure() {

        when(cacheProperties.ttl())
                .thenReturn(1);

        assertThat(cacheConfig.cacheManager(new LettuceConnectionFactory()))
                .isInstanceOfSatisfying(RedisCacheManager.class, redisCacheManager -> {

                    assertThat(redisCacheManager.getCache(CACHE_NAME))
                            .isInstanceOfSatisfying(RedisCache.class, redisCache -> {

                                assertThat(redisCache.getCacheConfiguration())
                                        .isInstanceOfSatisfying(RedisCacheConfiguration.class, redisCacheConfiguration -> {

                                            assertThat(redisCacheConfiguration.getTtlFunction())
                                                    .isEqualTo(RedisCacheWriter.TtlFunction.just(Duration.ofSeconds(1)));

                                            assertThat(redisCacheConfiguration.getAllowCacheNullValues())
                                                    .isFalse();

                                        });

                            });

                });

        verify(cacheProperties).ttl();

    }


}