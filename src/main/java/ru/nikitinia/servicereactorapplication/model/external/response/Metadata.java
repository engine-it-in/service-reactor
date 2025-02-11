package ru.nikitinia.servicereactorapplication.model.external.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Schema(
        name = "Список признаков",
        description = "Список обрабатываемых признаков"
)
@Builder
public record Metadata(

        @Schema(description = "Признаки")
        List<String> feature_names

) implements Serializable {

}
