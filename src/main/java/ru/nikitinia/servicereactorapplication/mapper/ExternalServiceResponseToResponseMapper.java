package ru.nikitinia.servicereactorapplication.mapper;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nikitinia.servicereactorapplication.model.external.response.ExternalServiceResponse;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;
import ru.nikitinia.servicereactorapplication.model.service.response.Response;

import java.util.List;

import static ru.nikitinia.servicereactorapplication.util.Constant.Feast.FEATURE;

@Mapper(
        componentModel = "spring",
        uses = {
                ExternalServiceResponseToListParameterMapper.class
        }
)
@Setter(onMethod = @__(@Autowired))
public abstract class ExternalServiceResponseToResponseMapper {

    private ExternalServiceResponseToListParameterMapper externalServiceResponseToListParameterMapper;

    @Mapping(target = "checkSuccess", constant = "true")
    @Mapping(target = "checkResult", source = "externalServiceResponse", qualifiedByName = "getListParameter")
    public abstract Response mapFeastResponseToResponse(ExternalServiceResponse externalServiceResponse);

    @Named("getListParameter")
    List<Parameter> getListParameter(ExternalServiceResponse externalServiceResponse) {
        return externalServiceResponseToListParameterMapper.getListParameterWithClassNameFromFeastResponse(FEATURE, externalServiceResponse);
    }

}