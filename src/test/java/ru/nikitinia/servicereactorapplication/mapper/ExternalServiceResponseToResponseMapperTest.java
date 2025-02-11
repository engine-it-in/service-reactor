package ru.nikitinia.servicereactorapplication.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;
import ru.nikitinia.servicereactorapplication.model.service.response.Response;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ru.nikitinia.servicereactorapplication.util.Constant.Feast.FEATURE;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ExternalServiceResponseToResponseMapperImpl.class})
class ExternalServiceResponseToResponseMapperTest {

    private ExternalServiceResponseToResponseMapper externalServiceResponseToResponseMapper;

    @MockitoBean
    private ExternalServiceResponseToListParameterMapper externalServiceResponseToListParameterMapper;

    @BeforeEach
    void setUp() {
        this.externalServiceResponseToResponseMapper = new ExternalServiceResponseToResponseMapperImpl();
        this.externalServiceResponseToResponseMapper.setExternalServiceResponseToListParameterMapper(externalServiceResponseToListParameterMapper);
    }

    @Test
    void mapFeastResponseToResponse_shouldMap() {

        final ExternalServiceResponse externalServiceResponse = TestDataBuilder.getTestFeastPersonResponse();
        final List<Parameter> parameterList = List.of(TestDataBuilder.getTestParameter());

        when(externalServiceResponseToListParameterMapper.getListParameterWithClassNameFromFeastResponse(FEATURE, externalServiceResponse))
                .thenReturn(parameterList);

        assertThat(externalServiceResponseToResponseMapper.mapFeastResponseToResponse(externalServiceResponse))
                .isInstanceOfSatisfying(Response.class, response -> {

                    assertThat(response.checkResult())
                            .isEqualTo(externalServiceResponseToResponseMapper.getListParameter(externalServiceResponse));

                    assertThat(response.checkSuccess())
                            .isEqualTo("true");

                });

        verify(externalServiceResponseToListParameterMapper, times(2))
                .getListParameterWithClassNameFromFeastResponse(FEATURE, externalServiceResponse);

    }

}
