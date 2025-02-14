package ru.nikitinia.servicereactorapplication.controller;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.nikitinia.servicereactorapplication.model.error.ErrorModel;
import ru.nikitinia.servicereactorapplication.model.service.request.Request;
import ru.nikitinia.servicereactorapplication.model.service.response.Response;
import ru.nikitinia.servicereactorapplication.service.ServiceReactorService;

import static ru.nikitinia.servicereactorapplication.util.Constant.ControllerTimedMetrics.TIME_METRIC_DESCRIPTION;
import static ru.nikitinia.servicereactorapplication.util.Constant.ControllerTimedMetrics.TIME_METRIC_NAME;

@Tag(
        name = "ExternalService",
        description = "Endpoint for operations"
)
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Timed(
        value = TIME_METRIC_NAME,
        description = TIME_METRIC_DESCRIPTION
)
public class ExternalServiceController {

    private final ServiceReactorService serviceReactorService;


    @Operation(
            tags = "external-service",
            summary = "Определение необходимых признаков",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD REQUEST",
                            content = @Content(schema = @Schema(implementation = ErrorModel.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "UNPROCESSABLE ENTITY",
                            content = @Content(schema = @Schema(implementation = ErrorModel.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "INTERNAL SERVER ERROR",
                            content = @Content(schema = @Schema(implementation = ErrorModel.class)))
            }
    )
    @PostMapping(
            value = "/do-it",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response> checkFeast(@Valid @RequestBody Request request) {
        return serviceReactorService
                .processingFeast(request);
    }

}