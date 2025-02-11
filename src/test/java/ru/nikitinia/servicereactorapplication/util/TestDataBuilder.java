package ru.nikitinia.servicereactorapplication.util;

import feign.RequestTemplate;
import lombok.experimental.UtilityClass;
import ru.nikitinia.servicereactorapplication.model.error.ErrorExternalServiceModel;
import ru.nikitinia.servicereactorapplication.model.error.ErrorModel;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.model.external.response.Metadata;
import ru.nikitinia.servicereactorapplication.model.external.response.Result;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.model.service.response.Response;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import static ru.nikitinia.servicereactorapplication.util.Constant.Feast.*;
import static ru.nikitinia.servicereactorapplication.util.Constant.ValidationParameters.*;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.TEST_MESSAGE;
import static ru.nikitinia.servicereactorapplication.util.TestConstant.ValidationConstant.*;

@UtilityClass
public class TestDataBuilder {

    public static ErrorExternalServiceModel getTestErrorFeastModel() {
        return ErrorExternalServiceModel.builder()
                .detail(TEST_MESSAGE)
                .build();
    }

    public static ErrorModel getTestErrorModel() {
        return ErrorModel.builder()
                .message(TEST_MESSAGE)
                .build();
    }

    public static feign.Response getTestFeignResponse(String body, feign.Request.HttpMethod method, int status) {
        return feign.Response.builder()
                .body(body, Charset.defaultCharset())
                .status(status)
                .request(feign.Request.create(
                        method,
                        "url",
                        new HashMap<>(),
                        feign.Request.Body.empty(),
                        new RequestTemplate()))
                .build();
    }

    public static Metadata getTestMetadata() {
        return Metadata.builder()
                .feature_names(List.of("feature_names"))
                .build();
    }

    public static Metadata getTestMetadataWithTwoValueInList() {
        return Metadata.builder()
                .feature_names(
                        List.of(
                                "feature_names",
                                TEST_MESSAGE
                        )
                )
                .build();
    }

    public static Result getTestResult() {
        return Result.builder()
                .values(List.of("values"))
                .statuses(List.of("statuses"))
                .event_timestamps(List.of("event_timestamps"))
                .build();
    }

    public static Result getTestResultFull() {
        return Result.builder()
                .values(List.of(TEST_MESSAGE, TEST_MESSAGE))
                .statuses(List.of(TEST_MESSAGE, TEST_MESSAGE))
                .event_timestamps(List.of(TEST_MESSAGE, TEST_MESSAGE))
                .build();
    }

    public static ExternalServiceResponse getTestFeastPersonResponse() {
        return new ExternalServiceResponse(
                getTestMetadata(),
                List.of(getTestResult())
        );
    }

    public static ExternalServiceResponse getTestFeastPersonResponseFull() {
        return new ExternalServiceResponse(
                getTestMetadataWithTwoValueInList(),
                List.of(getTestResultFull(), getTestResultFull())
        );
    }


    public HashMap<String, List<String>> getTestHashMapEntityWithPersonKey() {
        HashMap<String, List<String>> entity = new HashMap<>();
        entity.put("person_key", List.of("person_key"));
        return entity;
    }

    public static ExternalServiceRequest getTestFeastPersonRequest() {
        return new ExternalServiceRequest(
                List.of("features"),
                getTestHashMapEntityWithPersonKey()
        );
    }

    public static Request getTestRequestWithOutEntityId() {
        return new Request(
                List.of(getTestParameter())
        );
    }

    public static Request getTestRequestWithOneEntityId() {
        return new Request(
                List.of(getTestParameterWithEntityId())
        );
    }

    public static Request getTestRequestWithTwoEntityId() {
        return new Request(
                List.of(
                        getTestParameterWithEntityId(),
                        getTestParameterWithEntityId()
                )
        );
    }

    public static Request getTestRequestWithNotAllowedParameters() {
        return Request.builder()
                .parameter(
                        List.of(
                                getTestParameterWithNotValidInn(),
                                getTestParameterWithNotValidClaimSubclaim(),
                                getTestParameterWithNotValidPersonKey()
                        )
                )
                .build();
    }

    public static Request getTestSuccessRequestForRequestToFeastMapperWithInnKey() {
        return new Request(
                List.of(
                        getTestParameterFeaturesetForMapper(),
                        getTestParameterFeatureForMapper(),
                        getTestSuccessParameterEntityIdWithInn()
                )
        );

    }

    public static Request getTestSuccessRequestForRequestToFeastMapperWithPersonKey() {
        return new Request(
                List.of(
                        getTestParameterFeaturesetForMapper(),
                        getTestParameterFeatureForMapper(),
                        getTestSuccessParameterEntityIdWithPersonKey()
                )
        );

    }

    public static Request getTestSuccessRequestForRequestToFeastMapperWithClaimSubclaim() {
        return new Request(
                List.of(
                        getTestParameterFeaturesetForMapper(),
                        getTestParameterFeatureForMapper(),
                        getTestSuccessParameterEntityIdWithClaimSubclaim()
                )
        );
    }

    public static Request getTestFailFeaturesetRequestForRequestToFeastMapper() {
        return new Request(
                List.of(
                        getTestParameter()
                )
        );
    }


    private static Parameter getTestParameterFeaturesetForMapper() {
        return new Parameter(
                "className",
                FEATURESET,
                "value"
        );
    }

    public static Parameter getTestParameterWithNotValidInn() {
        return Parameter.builder()
                .className(PARAMETER_CLASS_NAME_INN)
                .name(PARAMETER_NAME_ENTITY_ID)
                .value(INN_NOT_VALID)
                .build();
    }

    public static Parameter getTestParameterWithNotValidPersonKey() {
        return Parameter.builder()
                .className(PARAMETER_CLASS_NAME_PERSON_KEY)
                .name(PARAMETER_NAME_ENTITY_ID)
                .value(PERSON_KEY_NOT_VALID)
                .build();
    }

    public static Parameter getTestParameterWithNotValidClaimSubclaim() {
        return Parameter.builder()
                .className(PARAMETER_CLASS_NAME_CLAIM_SUBCLAIM)
                .name(PARAMETER_NAME_ENTITY_ID)
                .value(CLAIM_SUBCLAIM_NOT_VALID)
                .build();
    }

    private static Parameter getTestParameterFeatureForMapper() {
        return new Parameter(
                "className",
                FEATURE,
                "value"
        );
    }

    private static Parameter getTestSuccessParameterEntityIdWithInn() {
        return new Parameter(
                PARAMETER_CLASS_NAME_INN,
                ENTITY_ID,
                "1234567890"
        );
    }

    private static Parameter getTestSuccessParameterEntityIdWithPersonKey() {
        return new Parameter(
                PARAMETER_CLASS_NAME_PERSON_KEY,
                ENTITY_ID,
                "Никитин Иван Андреевич 1986-12-09"
        );
    }

    private static Parameter getTestSuccessParameterEntityIdWithClaimSubclaim() {
        return new Parameter(
                PARAMETER_CLASS_NAME_CLAIM_SUBCLAIM,
                ENTITY_ID,
                "1234/1234/123_123"
        );
    }


    public static Parameter getTestParameter() {
        return new Parameter(
                "className",
                "name",
                "value"
        );
    }

    public static Parameter getTestParameterWithEntityId() {
        return new Parameter(
                "className",
                ENTITY_ID,
                "value"
        );
    }

    public static Response getTestResponse() {
        return new Response(
                List.of(getTestParameter()),
                String.valueOf(true)
        );
    }

    public static HashMap<String, List<String>> getTestValidPersonKeyMapWithValue(String valuePersonKey) {
        HashMap<String, List<String>> stringListHashMap = new HashMap<>();
        stringListHashMap.put(PARAMETER_NAME_PERSON_KEY, List.of(valuePersonKey));
        return stringListHashMap;
    }
}
