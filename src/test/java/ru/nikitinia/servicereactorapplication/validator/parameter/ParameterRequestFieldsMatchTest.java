package ru.nikitinia.servicereactorapplication.validator.parameter;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nikitinia.servicereactorapplication.controller.ExternalServiceController;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.service.ServiceReactorService;
import ru.nikitinia.servicereactorapplication.util.TestDataBuilder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.ENTITY_ID_MORE_ONE_EXCEPTION;
import static ru.nikitinia.servicereactorapplication.util.Constant.ExceptionText.Feast.VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE;
import static ru.nikitinia.servicereactorapplication.util.Constant.ValidationParameters.*;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.ValidationConstant.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ExternalServiceController.class)
class ParameterRequestFieldsMatchTest {

    @Autowired
    private Validator validator;

    @MockitoBean
    private ServiceReactorService serviceReactorService;

    @Test
    void checkParameterRequestFieldsMatch() {
        final Request request = TestDataBuilder.getTestRequestWithNotAllowedParameters();
        final Set<ConstraintViolation<Request>> violations = validator.validate(request);

        assertThat(violations)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4)
                .satisfies(
                        violation ->
                                assertThat(violation)
                                        .extracting(ConstraintViolation::getMessage)
                                        .contains(
                                                String.format(
                                                        VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE,
                                                        PARAMETER_CLASS_NAME_INN,
                                                        INN_NOT_VALID))
                                        .contains(
                                                String.format(
                                                        VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE,
                                                        PARAMETER_CLASS_NAME_PERSON_KEY,
                                                        PERSON_KEY_NOT_VALID)
                                        )
                                        .contains(
                                                String.format(
                                                        VALIDATION_PRE_FORMAT_MESSAGE_WITH_FIELD_AND_VALUE,
                                                        PARAMETER_CLASS_NAME_CLAIM_SUBCLAIM,
                                                        CLAIM_SUBCLAIM_NOT_VALID))
                                        .contains(
                                                ENTITY_ID_MORE_ONE_EXCEPTION
                                        )
                );

        verifyNoInteractions(serviceReactorService);
    }

}