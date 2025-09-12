package com.banking.eagle.api;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class ProblemDetails {
    private int status;
    private String errorCode;
    private String message;
    private String traceId;
    private Instant timestamp;
}
