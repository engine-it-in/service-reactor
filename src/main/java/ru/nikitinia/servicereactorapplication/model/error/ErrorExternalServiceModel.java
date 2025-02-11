package ru.nikitinia.servicereactorapplication.model.error;

import lombok.Builder;

@Builder
public record ErrorExternalServiceModel(
        String detail
) {
}
