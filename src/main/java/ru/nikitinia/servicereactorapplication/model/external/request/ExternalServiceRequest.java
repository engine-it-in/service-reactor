package ru.nikitinia.servicereactorapplication.model.external.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.HashMap;
import java.util.List;

@Schema(
        name = "Модель запроса",
        description = "Запрос для получения признаков"
)
@Builder
public record ExternalServiceRequest(

        @Schema(description = "Признаки в формате - таблица:признак")
        List<String> features,

        @Schema(description = "Сущности для запроса")
        HashMap<String, List<String>> entities
) {
}