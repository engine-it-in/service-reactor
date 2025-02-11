package ru.nikitinia.servicereactorapplication.model.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(
        name = "Модель ошибки",
        description = "Описание ошибки"
)
@Builder
public record ErrorModel(

        @Schema(description = "Описание ошибки")
        String message
) {
}
