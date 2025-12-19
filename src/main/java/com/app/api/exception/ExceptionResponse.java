package com.app.api.exception;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ExceptionResponse {
    private int status;
    private String message;
    private String error;
    private String path;
    private LocalDateTime timestamp = LocalDateTime.now();

}
