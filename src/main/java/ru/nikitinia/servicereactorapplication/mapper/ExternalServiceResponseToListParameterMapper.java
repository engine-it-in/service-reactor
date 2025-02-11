package ru.nikitinia.servicereactorapplication.mapper;

import org.springframework.stereotype.Component;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExternalServiceResponseToListParameterMapper {

    List<Parameter> getListParameterWithClassNameFromFeastResponse(String className, ExternalServiceResponse externalServiceResponse) {
        List<Parameter> parameterList = new ArrayList<>();
        for (int i = 1; i < externalServiceResponse.results().size(); i++) {
            parameterList.add(getParameter(externalServiceResponse, className, i));
        }
        return parameterList;
    }

    private Parameter getParameter(ExternalServiceResponse externalServiceResponse, String className, int itemNum) {
        return new Parameter(
                className,
                externalServiceResponse.metadata().feature_names().get(itemNum),
                externalServiceResponse.results().get(itemNum).values().get(0)
        );
    }

}