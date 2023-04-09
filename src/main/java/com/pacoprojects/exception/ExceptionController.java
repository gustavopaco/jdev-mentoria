package com.pacoprojects.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    @ExceptionHandler(value = {Exception.class, RuntimeException.class, Throwable.class})
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {

        StringBuilder message = new StringBuilder();

        if (exception instanceof HttpMessageNotReadableException) {
            message.append("Não está sendo enviado o BODY no corpo da requisição");
        } else {
            message.append(exception.getMessage());
            exception.printStackTrace();
        }


        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(message.toString())
                .code(statusCode.value())
                .build(), headers, statusCode);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

        StringBuilder message = new StringBuilder();

        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> message.append(objectError.getDefaultMessage()).append("\n"));
            String formatStringBuilder = message.substring(0, message.length() - 1);
            message.delete(0, message.length());
            message.append(formatStringBuilder);
        }

        return ResponseEntity
                .badRequest()
                .body(ExceptionObject
                        .builder()
                        .message(message.toString())
                        .code(HttpStatus.BAD_REQUEST.value())
                        .build());
    }


    @ExceptionHandler(value = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleExceptionResponseStatus(ResponseStatusException exception) {
        String message = exception.getReason();
        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(message)
                .code(exception.getStatusCode().value())
                .build(), exception.getStatusCode());
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class, SQLException.class, ConstraintViolationException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegrity(Exception exception) {
        StringBuilder message = new StringBuilder();

        if (exception instanceof DataIntegrityViolationException) {
            message.append(exception.getCause().getCause().getMessage());
        } else if (exception instanceof ConstraintViolationException) {
            message.append(exception.getCause().getCause().getMessage());
        } else if (exception instanceof SQLException) {
            message.append(exception.getCause().getCause().getMessage());
        } else {
            message.append(exception.getMessage());
        }

        return ResponseEntity
                .internalServerError()
                .body(ExceptionObject
                        .builder()
                        .message(message.toString())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build());
    }
}
