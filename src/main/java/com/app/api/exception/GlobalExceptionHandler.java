package com.app.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> NotFoundException(NotFoundException ex, WebRequest request){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(404)
                .error("not found")
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidData(InvalidDataException ex, WebRequest request){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(406)
                .error("not acceptable value or content")
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex, WebRequest request){
        Map<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap.put(error.getField(),error.getDefaultMessage());
        });
        return new ResponseEntity<>(errorsMap,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex,WebRequest request){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(404)
                .error("not found")
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionResponse> handleIllegaleStateException(IllegalStateException ex, WebRequest request){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(406)
                .error("Illegal content")
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ExceptionResponse> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex, WebRequest request){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(406)
                .error("Email Already used")
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
    }
}
