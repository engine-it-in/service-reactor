package ru.nikitinia.servicereactorapplication.validator.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nikitinia.servicereactorapplication.controller.ExternalServiceController;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.service.ServiceReactorService;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.ENTITY_ID_MORE_ONE_EXCEPTION;
import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.ENTITY_ID_NO_EXCEPTION;

import java.util.Set;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ExternalServiceController.class)
class RequestParameterEntityMatchTest {

    @Autowired
    private Validator validator;

    @MockitoBean
    private ServiceReactorService serviceReactorService;

    @ParameterizedTest
    @MethodSource("provideRequestAndTestDataForCheckViolations")
    void checkRequestNoContainsEntityId(Request request, String exceptionMessage) {
        final Set<ConstraintViolation<Request>> violations = validator.validate(request);

        assertThat(violations)
                .hasSize(1)
                .satisfies(
                        violation ->
                                assertThat(violation)
                                        .extracting(ConstraintViolation::getMessage)
                                        .contains(
                                                exceptionMessage
                                        )
                );

        verifyNoInteractions(serviceReactorService);

    }

    private static Stream<Arguments> provideRequestAndTestDataForCheckViolations() {
        return Stream.of(
                Arguments.of(TestDataBuilder.getTestRequestWithOutEntityId(), ENTITY_ID_NO_EXCEPTION),
                Arguments.of(TestDataBuilder.getTestRequestWithTwoEntityId(), ENTITY_ID_MORE_ONE_EXCEPTION)
        );
    }

}