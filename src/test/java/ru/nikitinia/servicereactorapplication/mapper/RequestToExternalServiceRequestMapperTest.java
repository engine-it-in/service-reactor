package ru.nikitinia.servicereactorapplication.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nikitinia.servicereactorapplication.mapper.component.PersonKeyDataNormalizer;
import ru.nikitinia.servicereactorapplication.exception.ServiceReactorLogicException;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.nikitinia.servicereactorapplication.util.Constant.Feast.FEATURESET;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;
import static ru.nikitinia.servicereactorapplication.util.TestDataBuilder.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RequestToExternalServiceRequestMapperImpl.class})
class RequestToExternalServiceRequestMapperTest {

    private RequestToExternalServiceRequestMapper mapper;

    @MockitoBean
    private PersonKeyDataNormalizer personKeyDataNormalizer;


    @BeforeEach
    void setUp() {
        mapper = new RequestToExternalServiceRequestMapperImpl();
        mapper.setPersonKeyDataNormalizer(personKeyDataNormalizer);
    }

    @Test
    void mapRequestToFeast_shouldThrownFeastOnlineLogicExceptionBecauseNoFeatureset() {
        final Request request = getTestFailFeaturesetRequestForRequestToFeastMapper();
        final ServiceReactorLogicException serviceReactorLogicException = new ServiceReactorLogicException(String
                .join(SPACE, "Значение для", FEATURESET, "не определено"));

        assertThatThrownBy(() -> mapper.mapRequestToFeast(request))
                .isInstanceOf(ServiceReactorLogicException.class)
                .hasMessageContaining(serviceReactorLogicException.getMessage());

        verifyNoInteractions(personKeyDataNormalizer);

    }

    @Test
    void mapRequestToFeast_shouldThrownFeastOnlineLogicExceptionBecausePersonKeyNotValid() {
        final Request request = getTestSuccessRequestForRequestToFeastMapperWithPersonKey();
        final ServiceReactorLogicException serviceReactorLogicException = new ServiceReactorLogicException(TEST_MESSAGE);

        when(personKeyDataNormalizer.normalizeParameter(any()))
                .thenThrow(serviceReactorLogicException);

        assertThatThrownBy(() -> mapper.mapRequestToFeast(request))
                .isInstanceOf(serviceReactorLogicException.getClass())
                .hasMessageContaining(serviceReactorLogicException.getMessage());

        verify(personKeyDataNormalizer).normalizeParameter(any());

    }

    @ParameterizedTest
    @MethodSource("provideRequestDataForSuccessMap")
    void mapRequestToFeast_shouldMapInn(Request request) {

        assertThat(mapper.mapRequestToFeast(request))
                .isInstanceOfSatisfying(ExternalServiceRequest.class, feastPersonRequest -> {

                    assertThat(feastPersonRequest.entities())
                            .isInstanceOfSatisfying(HashMap.class, entity ->

                                    assertThat(entity)
                                            .isEqualTo(mapper.getEntity(request)));

                    assertThat(feastPersonRequest.features())
                            .isEqualTo(mapper.getFeatures(request));

                });

        verify(personKeyDataNormalizer, times(2))
                .normalizeParameter(mapper.getEntity(request));
    }

    private static Stream<Arguments> provideRequestDataForSuccessMap() {
        return Stream.of(
                Arguments.of(getTestSuccessRequestForRequestToFeastMapperWithInnKey()),
                Arguments.of(getTestSuccessRequestForRequestToFeastMapperWithPersonKey()),
                Arguments.of(getTestSuccessRequestForRequestToFeastMapperWithClaimSubclaim())
        );
    }

}
