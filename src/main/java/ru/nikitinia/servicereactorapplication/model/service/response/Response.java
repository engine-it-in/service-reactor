package ru.nikitinia.servicereactorapplication.model.service.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.nikitinia.servicereactorapplication.model.service.Parameter;

import java.io.Serializable;
import java.util.List;

@Schema(
        name = "Результат проверки данных",
        description = "Характеристики признаков"
)
@Builder
public record Response (
        @Schema(description = "Список признаков")
        List<Parameter> checkResult,

        @Schema(description = "Признак успешности проверки")
        String checkSuccess

) implements Serializable {
}
