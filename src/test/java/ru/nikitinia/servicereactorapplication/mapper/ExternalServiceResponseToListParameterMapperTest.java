package ru.nikitinia.servicereactorapplication.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ExternalServiceResponseToListParameterMapper.class})
class ExternalServiceResponseToListParameterMapperTest {

    @Autowired
    private ExternalServiceResponseToListParameterMapper externalServiceResponseToListParameterMapper;

    @Test
    void checkFeastResponseToListParameterMapper() {
        assertThat(externalServiceResponseToListParameterMapper)
                .hasAllNullFieldsOrProperties();
    }

    @Test
    void getListParameterWithClassNameFromFeastResponse_shouldReturnResult() {
        final ExternalServiceResponse externalServiceResponse = TestDataBuilder.getTestFeastPersonResponseFull();

        assertThat(externalServiceResponseToListParameterMapper.getListParameterWithClassNameFromFeastResponse(TEST_MESSAGE, externalServiceResponse))
                .usingRecursiveComparison()
                .isEqualTo(List.of(
                        Parameter.builder()
                                .className(TEST_MESSAGE)
                                .name(TEST_MESSAGE)
                                .value(TEST_MESSAGE)
                                .build()
                ));

    }

}