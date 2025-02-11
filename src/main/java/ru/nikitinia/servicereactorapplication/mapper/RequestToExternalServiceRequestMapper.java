package ru.nikitinia.servicereactorapplication.mapper;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nikitinia.servicereactorapplication.mapper.component.PersonKeyDataNormalizer;
import ru.nikitinia.servicereactorapplication.exception.ServiceReactorLogicException;
import ru.nikitinia.servicereactorapplication.model.external.request.ExternalServiceRequest;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;

import java.util.HashMap;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static ru.nikitinia.servicereactorapplication.util.Constant.Feast.*;
import static ru.nikitinia.servicereactorapplication.util.Constant.NormalizeValue.COLON;

@Mapper(
        componentModel = "spring",
        uses = {PersonKeyDataNormalizer.class}
)
@Setter(onMethod = @__(@Autowired))
public abstract class RequestToExternalServiceRequestMapper {

    private PersonKeyDataNormalizer personKeyDataNormalizer;

    @Mapping(target = "features", source = "request", qualifiedByName = "getFeatures")
    @Mapping(target = "entities", source = "request", qualifiedByName = "getEntity")
    public abstract ExternalServiceRequest mapRequestToFeast(Request request);

    @Named("getFeatures")
    List<String> getFeatures(Request request) {
        String featureset = request.parameter()
                .stream()
                .filter(parameter -> parameter.name().equals(FEATURESET))
                .findFirst()
                .map(Parameter::value)
                .orElseThrow(() -> new ServiceReactorLogicException(String
                        .join(SPACE, "Значение для", FEATURESET, "не определено")));

        List<String> features = request.parameter()
                .stream()
                .filter(parameter -> parameter.name().equals(FEATURE))
                .map(Parameter::value)
                .toList();

        return features.stream()
                .map(s -> String.join(COLON, featureset, s))
                .toList();
    }

    @Named("getEntity")
    HashMap<String, List<String>> getEntity(Request request) {
        HashMap<String, List<String>> entity = new HashMap<>();
        entity.put(getEntityKey(request), getValueForEntity(request));
        return (HashMap<String, List<String>>) personKeyDataNormalizer.normalizeParameter(entity);
    }

    List<String> getValueForEntity(Request request) {
        return request.parameter().stream()
                .filter(parameter -> parameter.name().equals(ENTITY_ID))
                .map(Parameter::value)
                .toList();
    }

    String getEntityKey(Request request) {
        return request.parameter().stream()
                .filter(parameter -> parameter.name().equals(ENTITY_ID))
                .map(Parameter::className)
                .toList()
                .get(0);
    }

}