package ru.nikitinia.servicereactorapplication.model.service.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Builder;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;
import ru.nikitinia.servicereactorapplication.validator.request.RequestParameterEntityMatch;

import java.util.List;

@Schema(
        name = "Запрос",
        description = "Набор данных для проверки в Feast"
)
@Builder
@RequestParameterEntityMatch
public record Request(

        @Schema(description = "Набор признаков для проверки")
        @Valid
        List<Parameter> parameter
) {
}
