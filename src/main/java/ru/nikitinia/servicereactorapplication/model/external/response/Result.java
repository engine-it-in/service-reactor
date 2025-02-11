package ru.nikitinia.servicereactorapplication.model.external.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Schema(
        name = "Модель представления результата",
        description = "Данные по переданным на обработку признакам"
)
@Builder
public record Result(

        @Schema(description = "Значения")
        List<String> values,

        @Schema(description = "Статусы")
        List<String> statuses,

        @Schema(description = "Временная метка")
        List<String> event_timestamps

) implements Serializable {
}
