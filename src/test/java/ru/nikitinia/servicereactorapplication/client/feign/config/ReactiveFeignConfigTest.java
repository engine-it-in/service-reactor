package ru.nikitinia.servicereactorapplication.client.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ReactiveFeignConfig.class)
class ReactiveFeignConfigTest {

    @Autowired
    private ReactiveFeignConfig reactiveFeignConfig;


    @MockitoBean
    private ObjectMapper objectMapper;

    @Test
    void checkReactiveFeignConfig() {
        assertThat(reactiveFeignConfig)
                .hasFieldOrPropertyWithValue("objectMapper", objectMapper)
                .hasNoNullFieldsOrProperties()
        ;
    }

    @Test
    void errorDecoder_shouldReturnResult() {
        assertThat(reactiveFeignConfig.errorDecoder())
                .isInstanceOfSatisfying(ReactiveFeignErrorDecoder.class, reactiveFeignErrorDecoder ->  {

                    assertThat(reactiveFeignErrorDecoder)
                            .hasFieldOrPropertyWithValue("objectMapper", objectMapper);

                });
    }

}