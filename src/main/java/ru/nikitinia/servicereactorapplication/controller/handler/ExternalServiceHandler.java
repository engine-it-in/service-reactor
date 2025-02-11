package ru.nikitinia.servicereactorapplication.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceClientException;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceConnectionException;
import ru.nikitinia.servicereactorapplication.exception.ServiceReactorLogicException;
import ru.nikitinia.servicereactorapplication.exception.ExternalServiceServerException;
import ru.nikitinia.servicereactorapplication.model.error.ErrorModel;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static ru.nikitinia.servicereactorapplication.util.Constant.MainValue.SEPARATOR_FOR_METHOD_NOT_ALLOWED_ARGUMENT;

@ControllerAdvice
public class ExternalServiceHandler {

    @ExceptionHandler({ServiceReactorLogicException.class})
    public ResponseEntity<ErrorModel> handleFeastOnlineLogicException(ServiceReactorLogicException exception) {
        ErrorModel errorModel = new ErrorModel(exception.getMessage());
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(errorModel);
    }

    @ExceptionHandler(ExternalServiceClientException.class)
    public ResponseEntity<ErrorModel> handleFeastClientException(ExternalServiceClientException externalServiceClientException) {
        ErrorModel errorModel = new ErrorModel(externalServiceClientException.getMessage());
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(errorModel);
    }

    @ExceptionHandler(ExternalServiceServerException.class)
    public ResponseEntity<ErrorModel> handleFeastServerException(ExternalServiceServerException feastServerException) {
        ErrorModel errorModel = new ErrorModel(feastServerException.getMessage());
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(errorModel);
    }

    @ExceptionHandler(ExternalServiceConnectionException.class)
    public ResponseEntity<ErrorModel> handleFeastException(ExternalServiceConnectionException externalServiceConnectionException) {
        ErrorModel errorModel = new ErrorModel(externalServiceConnectionException.getMessage());
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(errorModel);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorModel> handleWebExchangeBindException(WebExchangeBindException exception) {
        List<String> messageList = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach(
                objectError ->
                        messageList.add(objectError.getDefaultMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorModel.builder()
                        .message(
                                String.join(SEPARATOR_FOR_METHOD_NOT_ALLOWED_ARGUMENT, messageList)
                        )
                        .build());
    }

}