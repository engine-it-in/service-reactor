package ru.nikitinia.servicereactorapplication.model.external.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Schema(
        name = "Модель ответа",
        description = "Признаки"
)

public record ExternalServiceResponse(

        @Schema(description = "Название признаков")
        Metadata metadata,

        @Schema(description = "Результат обработки признаков")
        List<Result> results

) implements Serializable {
}
