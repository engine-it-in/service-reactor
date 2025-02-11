package ru.nikitinia.servicereactorapplication.model.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.nikitinia.servicereactorapplication.validator.parameter.ParameterRequestFieldsMatch;

import java.io.Serializable;

@Schema(
        name = "Набор параметров, характеризующий признак",
        description = "Характеристики признака"
)
@Valid
@Builder
@ParameterRequestFieldsMatch
public record Parameter(

        @Schema(description = "Отношение признака")
        @NotNull
        String className,
        @Schema(description = "Название признака")
        String name,
        @Schema(description = "Значение признака")
        String value
) implements Serializable {
}
